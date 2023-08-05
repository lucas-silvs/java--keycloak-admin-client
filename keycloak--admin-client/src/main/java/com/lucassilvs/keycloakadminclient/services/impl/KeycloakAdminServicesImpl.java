package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.ApplicationException;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakClientModelDto;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakRealmRoleModelDto;
import com.lucassilvs.keycloakadminclient.datasource.entity.RealmAdminClientEntity;
import com.lucassilvs.keycloakadminclient.datasource.repository.RealmAdminClientRepository;
import com.lucassilvs.keycloakadminclient.services.KeycloakAdminServices;
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

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Profile("local")
public class KeycloakAdminServicesImpl implements KeycloakAdminServices {

    private final RealmAdminClientRepository realmAdminClientRepository;
    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminServicesImpl.class);

    @Autowired
    public KeycloakAdminServicesImpl(RealmAdminClientRepository realmAdminClientRepository) {
        this.realmAdminClientRepository = realmAdminClientRepository;
    }

    @Override
    public void criarClientCredentials(String realm, KeycloakClientModelDto keycloakClientModelDto) {
        createClientCredentialKeycloak(realm, keycloakClientModelDto);
    }

    protected ClientRepresentation createClientCredentialKeycloak(String realm, KeycloakClientModelDto keycloakClientModelDto) {

        Keycloak keycloakClient = getKeycloakClient(realm);

        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(keycloakClientModelDto.clientId());
        clientRepresentation.setServiceAccountsEnabled(true);
        clientRepresentation.setClientAuthenticatorType("client-secret");

        Response response = keycloakClient.realm(realm).clients().create(clientRepresentation);

        switch (response.getStatus()) {
            case 201 -> logger.info("Client credentials criado com sucesso");
            case 409 ->
                    throw new ApplicationException(String.format("client credentials %s já existe", keycloakClientModelDto.clientId()), HttpStatus.CONFLICT);
            default ->
                    throw new ApplicationException(String.format("Erro ao criar client credentials, recebido seguinte status: %s", response.getStatus()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        clientRepresentation = keycloakClient.realm(realm).clients().findByClientId(keycloakClientModelDto.clientId()).get(0);

        keycloakClient.close();
        return clientRepresentation;
    }

    @Override
    public KeycloakClientModelDto buscarClientCredentials(String realm, String clientId) {

        Keycloak keycloakClient = getKeycloakClient(realm);

        ClientRepresentation client = keycloakClient.realm(realm).clients().findByClientId(clientId).get(0);

        if(client == null) {
            throw new ApplicationException(String.format("Client %s não encontrado", clientId), HttpStatus.NOT_FOUND);
        }

        ClientResource clientResource = keycloakClient.realm(realm).clients().get(client.getId());
        UserRepresentation serviceAccountUser = clientResource.getServiceAccountUser();

        List<KeycloakRealmRoleModelDto> listrolesDTO = new ArrayList<>();
        keycloakClient.realm(realm).users().get(serviceAccountUser.getId()).roles().getAll().getRealmMappings().forEach(roles -> {
            List<String> attributes = keycloakClient.realm(realm).roles().get(roles.getName()).toRepresentation().getAttributes().get("poolId");
            String poolId = attributes != null ? attributes.get(0) : null;
            KeycloakRealmRoleModelDto roleDTO = new KeycloakRealmRoleModelDto(roles.getName(), roles.getDescription(), poolId );
            listrolesDTO.add(roleDTO);
        });

        keycloakClient.close();
        return new KeycloakClientModelDto(client.getClientId(), client.getSecret(), listrolesDTO);
    }

    public void atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole) {

        Keycloak keycloakClient = getKeycloakClient(realm);

        ClientRepresentation clientRepresentation = keycloakClient.realm(realm).clients().findByClientId(clientId).get(0);

        //  obtenha o service account user do client
        ClientResource clientResource = keycloakClient.realm(realm).clients().get(clientRepresentation.getId());

        UserRepresentation serviceAccountUser = clientResource.getServiceAccountUser();
        // busca realm role pelo nome
        RoleRepresentation realmRole = keycloakClient.realm(realm).roles().get(nomeRole).toRepresentation();

        keycloakClient.realm(realm).users().get(serviceAccountUser.getId()).roles().realmLevel().add(List.of(realmRole));

        logger.info(String.format("Realm role %s atribuida com sucesso", nomeRole));
        keycloakClient.close();
    }


    @Override
    public void criarRealmRole(String realm, KeycloakRealmRoleModelDto keycloakRealmRoleModelDto) {
        Keycloak keycloakClient = getKeycloakClient(realm);

        RoleRepresentation realmRole = new RoleRepresentation();
        realmRole.setName(keycloakRealmRoleModelDto.nomeRole());
        realmRole.setDescription(keycloakRealmRoleModelDto.descricaoRole());
        realmRole.setAttributes(Map.of("poolId", List.of(keycloakRealmRoleModelDto.poolId())));

        keycloakClient.realm(realm).roles().create(realmRole);

        keycloakClient.close();
    }

    @Override
    public KeycloakRealmRoleModelDto buscaRealmRole(String realm, String nomeRole) {

        Keycloak keycloakClient = getKeycloakClient(realm);

        RoleRepresentation realmRole = keycloakClient.realm(realm).roles().get(nomeRole).toRepresentation();

        keycloakClient.close();

        if (realmRole != null) {
            return new KeycloakRealmRoleModelDto(realmRole.getName(), realmRole.getDescription(), realmRole.getAttributes().get("poolId").toString());
        }

        throw new ApplicationException(String.format("Realm role %s não encontrado", nomeRole), HttpStatus.NOT_FOUND);
    }

    protected Keycloak getKeycloakClient(String realm) {

        Optional<RealmAdminClientEntity> entity = realmAdminClientRepository.findByRealm(realm);

        if(entity.isEmpty()) {
            throw new ApplicationException(String.format("Realm %s não encontrado", realm), HttpStatus.NOT_FOUND);
        }

        RealmAdminClientEntity realmAdminClientEntity = entity.get();

        return KeycloakBuilder.builder()
                .clientId(realmAdminClientEntity.getClientId())
                .clientSecret(realmAdminClientEntity.getClientSecret())
                .grantType(realmAdminClientEntity.getGrantType())
                .realm(realmAdminClientEntity.getRealm())
                .serverUrl(realmAdminClientEntity.getServerUrl())
                .build();

    }
}
