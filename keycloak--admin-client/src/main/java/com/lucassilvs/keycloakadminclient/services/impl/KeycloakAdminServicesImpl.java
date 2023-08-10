package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.ApplicationException;
import com.lucassilvs.keycloakadminclient.controller.mapper.ClientCredentialMapper;
import com.lucassilvs.keycloakadminclient.datasource.entity.AdminClientCredentialEntity;
import com.lucassilvs.keycloakadminclient.datasource.repository.RealmAdminClientRepository;
import com.lucassilvs.keycloakadminclient.services.KeycloakAdminServices;
import com.lucassilvs.keycloakadminclient.services.model.ClientCredential;
import com.lucassilvs.keycloakadminclient.services.model.RealmRole;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Profile("local")
public class KeycloakAdminServicesImpl implements KeycloakAdminServices {

    private final RealmAdminClientRepository realmAdminClientRepository;

    private final ClientCredentialMapper  clientCredentialMapper = ClientCredentialMapper.INSTANCE;


    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminServicesImpl.class);

    @Autowired
    public KeycloakAdminServicesImpl(RealmAdminClientRepository realmAdminClientRepository) {
        this.realmAdminClientRepository = realmAdminClientRepository;
    }

    public void criarClientCredentials(String realm, ClientCredential clientCredential) {
        createClientCredentialKeycloak(realm, clientCredential);
    }

    protected ClientRepresentation createClientCredentialKeycloak(String realm, ClientCredential clientCredential) {

        Keycloak keycloakClient = getKeycloakClient(realm);

        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(clientCredential.clientId());
        clientRepresentation.setServiceAccountsEnabled(true);
        clientRepresentation.setClientAuthenticatorType("client-secret");

        Response response = keycloakClient.realm(realm).clients().create(clientRepresentation);

        switch (response.getStatus()) {
            case 201 -> logger.info("Client credentials criado com sucesso");
            case 409 ->
                    throw new ApplicationException(String.format("client credentials %s já existe", clientCredential.clientId()), HttpStatus.CONFLICT);
            default ->
                    throw new ApplicationException(String.format("Erro ao criar client credentials, recebido seguinte status: %s", response.getStatus()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        clientRepresentation = keycloakClient.realm(realm).clients().findByClientId(clientCredential.clientId()).get(0);

        keycloakClient.close();
        return clientRepresentation;
    }

    @Override
    public ClientCredential buscarClientCredentials(String realm, String clientId) {

        Keycloak keycloakClient = getKeycloakClient(realm);

        ClientRepresentation client = getClientRepresentation(realm, clientId, keycloakClient);
        UserRepresentation serviceAccountUser = getServiceAccountClientCredential(realm, keycloakClient, client);

        //TODO refatorar para utilizar Mapper do MapStruct
        List<RealmRole> listroles = new ArrayList<>();
        keycloakClient.realm(realm).users().get(serviceAccountUser.getId()).roles().getAll().getRealmMappings().forEach(roles -> {
            // mapear cada atributo do realm role
            Map<String,List<String>> attributes = keycloakClient.realm(realm).roles().get(roles.getName()).toRepresentation().getAttributes();
            RealmRole role = new RealmRole(roles.getName(), roles.getDescription(), attributes);
            listroles.add(role);
        });

        keycloakClient.close();
        return new ClientCredential(client.getClientId(), client.getSecret(), listroles, null);
    }

    public void atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole) {

        Keycloak keycloakClient = getKeycloakClient(realm);

        ClientRepresentation clientRepresentation = getClientRepresentation(realm, clientId, keycloakClient);

        //  obtenha o service account user do client
        UserRepresentation serviceAccountUser = getServiceAccountClientCredential(realm, keycloakClient, clientRepresentation);

        // busca realm role pelo nome
        RoleRepresentation realmRole = getRoleRepresentation(realm, nomeRole, keycloakClient);

        keycloakClient.realm(realm).users().get(serviceAccountUser.getId()).roles().realmLevel().add(List.of(realmRole));

        logger.info(String.format("Realm role %s atribuida com sucesso", nomeRole));
        keycloakClient.close();
    }

    private static UserRepresentation getServiceAccountClientCredential(String realm, Keycloak keycloakClient, ClientRepresentation clientRepresentation) {
        ClientResource clientResource = keycloakClient.realm(realm).clients().get(clientRepresentation.getId());
       return clientResource.getServiceAccountUser();
    }


    @Override
    public void criarRealmRole(String realm, RealmRole realmRole) {
        Keycloak keycloakClient = getKeycloakClient(realm);

        RoleRepresentation realmRoleData = new RoleRepresentation();
        realmRoleData.setName(realmRole.nome());
        realmRoleData.setDescription(realmRole.descricao());
        realmRoleData.setAttributes(realmRole.atributos());

        try {
            keycloakClient.realm(realm).roles().create(realmRoleData);
        }catch (ClientErrorException e){
            throw new ApplicationException(String.format("Realm role %s já existe", realmRole.nome()), HttpStatus.CONFLICT);
        }catch (Exception e) {
            throw new ApplicationException(String.format("Erro ao criar realm role %s", realmRole.nome()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        keycloakClient.close();
    }

    @Override
    public RealmRole buscaRealmRole(String realm, String nomeRole) {

        Keycloak keycloakClient = getKeycloakClient(realm);
        RoleRepresentation realmRole = getRoleRepresentation(realm, nomeRole, keycloakClient);
        keycloakClient.close();

        return new RealmRole(realmRole.getName(), realmRole.getDescription(), realmRole.getAttributes());
    }

    private RoleRepresentation getRoleRepresentation(String realm, String nomeRole, Keycloak keycloakClient) {
        try {
            return keycloakClient.realm(realm).roles().get(nomeRole).toRepresentation();
        }catch (NotFoundException ex){
            throw new ApplicationException(String.format("Realm role %s não encontrado", nomeRole), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.warn(String.format("Erro ao buscar realm role:  %s", e.getMessage()));
            throw new ApplicationException(String.format("Erro ao buscar realm role %s", nomeRole), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    protected Keycloak getKeycloakClient(String realm) {

        Optional<AdminClientCredentialEntity> entity = realmAdminClientRepository.findByRealm(realm);
        if(entity.isEmpty()) {
            throw new ApplicationException(String.format("Realm %s não encontrado", realm), HttpStatus.NOT_FOUND);
        }
        AdminClientCredentialEntity adminClientCredentialEntity = entity.get();
        return KeycloakBuilder.builder()
                .clientId(adminClientCredentialEntity.getClientId())
                .clientSecret(adminClientCredentialEntity.getClientSecret())
                .grantType(adminClientCredentialEntity.getGrantType())
                .realm(adminClientCredentialEntity.getRealm())
                .serverUrl(adminClientCredentialEntity.getServerUrl())
                .build();
    }

    private ClientRepresentation getClientRepresentation(String realm, String clientId, Keycloak keycloakClient) {
        try {
           return keycloakClient.realm(realm).clients().findByClientId(clientId).get(0);
        }catch(NotFoundException ex) {
            throw new ApplicationException(String.format("Client %s não encontrado", clientId), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            logger.warn(String.format("Erro ao buscar client:  %s", e.getMessage()));
            throw new ApplicationException(String.format("Erro ao buscar client %s", clientId), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
