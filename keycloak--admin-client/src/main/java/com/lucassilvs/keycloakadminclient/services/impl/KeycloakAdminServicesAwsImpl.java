package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.DomainException;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakClientModelDto;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakRealmRoleModelDto;
import com.lucassilvs.keycloakadminclient.services.KeycloakAdminServices;
import org.keycloak.admin.client.Keycloak;
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
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Profile("localstack")
public class KeycloakAdminServicesAwsImpl extends KeycloakAdminServicesImpl implements KeycloakAdminServices {

    private final SecretsManagerClient secretsManagerClient;
    private static final String CREDENTIAL_SECRET_VALUE = "{ \"client-id\": \"%s\", \"client-secret\": \"%s\" }";
    private static final String SECRET_NAME = "keycloak/%s/%s";
    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminServicesAwsImpl.class);



    @Autowired
    public KeycloakAdminServicesAwsImpl(Keycloak keycloakClient, SecretsManagerClient secretsManagerClient) {
        super(keycloakClient);
        this.secretsManagerClient = secretsManagerClient;
    }

    @Override
    public void criarClientCredentials(String realm, KeycloakClientModelDto keycloakClientModelDto) {

        ClientRepresentation clientRepresentation = createClientCredentialKeycloak(realm, keycloakClientModelDto);

        createSecret(realm, keycloakClientModelDto, clientRepresentation);

        logger.info("Client credentials criado com sucesso");
    }

    private void createSecret(String realm, KeycloakClientModelDto keycloakClientModelDto, ClientRepresentation clientRepresentation) {
        CreateSecretRequest secretRequest = CreateSecretRequest.builder()
                .name(String.format(SECRET_NAME, realm, keycloakClientModelDto.clientId()))
                .secretString(String.format(CREDENTIAL_SECRET_VALUE, keycloakClientModelDto.clientId(), clientRepresentation.getSecret()))
                .build();
        try {
            secretsManagerClient.createSecret(secretRequest);
        } catch (Exception e) {
            logger.error("Erro ao criar secret no Secrets Manager", e);
            throw new DomainException(String.format("Erro ao criar secret no Secrets Manager: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
