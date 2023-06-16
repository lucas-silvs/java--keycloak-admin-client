package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakClientModelDto;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakRealmRoleModelDto;
import com.lucassilvs.keycloakadminclient.services.KeycloakAdminServices;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.ProtocolMapperRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakAdminServicesImpl implements KeycloakAdminServices {


    private final Keycloak keycloakClient;

    public KeycloakAdminServicesImpl(Keycloak keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminServicesImpl.class);


    @Override
    public void criarClientCredentials(String realm, KeycloakClientModelDto keycloakClientModelDto) {

        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(keycloakClientModelDto.getClientId());
        clientRepresentation.setServiceAccountsEnabled(true);
        clientRepresentation.setClientAuthenticatorType("client-secret");

        ProtocolMapperRepresentation protocolMapperRepresentation = new ProtocolMapperRepresentation();
        protocolMapperRepresentation.setName("subdominio mapper");
        protocolMapperRepresentation.setProtocol("openid-connect");
        protocolMapperRepresentation.setProtocolMapper("oidc-usermodel-realm-role-mapper");

        HashMap<String, String> config = new HashMap<>();
        config.put("id.token.claim", "true");
        config.put("access.token.claim", "true");
        config.put("claim.name", "subdomain");
        config.put("multivalued", "true");
        config.put("userinfo.token.claim", "true");
        protocolMapperRepresentation.setConfig(config);

        clientRepresentation.setProtocolMappers(List.of(protocolMapperRepresentation));


        Response response = keycloakClient.realm(realm).clients().create(clientRepresentation);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Erro ao criar client credentials");
        }

        logger.info("Client credentials criado com sucesso");



    }

    @Override
    public KeycloakClientModelDto buscarClientCredentials(String realm, String clientId) {

        ClientRepresentation client = keycloakClient.realm(realm).clients().findByClientId(clientId).get(0);

        if (client != null) {
            return new KeycloakClientModelDto(client.getClientId(), client.getSecret());
        }

        throw new RuntimeException("Client não encontrado");
    }

    @Override
    public void criarRealmRole(String realm, KeycloakRealmRoleModelDto keycloakRealmRoleModelDto) {

        RoleRepresentation realmRole = new RoleRepresentation();
        realmRole.setName(keycloakRealmRoleModelDto.getNomeRole());
        realmRole.setDescription(keycloakRealmRoleModelDto.getDescricaoRole());
        realmRole.setAttributes(Map.of("poolId", List.of(keycloakRealmRoleModelDto.getPoolId())));

        keycloakClient.realm(realm).roles().create(realmRole);

    }

    @Override
    public KeycloakRealmRoleModelDto buscaRealmRole(String realm, String nomeRole) {

        RoleRepresentation realmRole = keycloakClient.realm(realm).roles().get(nomeRole).toRepresentation();

        if (realmRole != null) {
            return new KeycloakRealmRoleModelDto(realmRole.getName(), realmRole.getDescription(), realmRole.getAttributes().get("poolId").toString());
        }

        throw new RuntimeException("Role não encontrada");
    }
}
