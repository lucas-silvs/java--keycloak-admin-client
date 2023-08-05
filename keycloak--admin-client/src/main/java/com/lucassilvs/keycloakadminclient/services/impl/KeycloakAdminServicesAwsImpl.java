package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.DomainException;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakClientModelDto;
import com.lucassilvs.keycloakadminclient.datasource.repository.RealmAdminClientRepository;
import com.lucassilvs.keycloakadminclient.services.KeycloakAdminServices;
import org.keycloak.representations.idm.ClientRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.ResourceExistsException;

@Service
@Profile("localstack")
public class KeycloakAdminServicesAwsImpl extends KeycloakAdminServicesImpl implements KeycloakAdminServices {


    private final SecretsManagerClient secretsManagerClient;
    private static final String CREDENTIAL_SECRET_VALUE = "{ \"client-id\": \"%s\", \"client-secret\": \"%s\" }";
    private static final String SECRET_NAME = "keycloak/%s/%s";
    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminServicesAwsImpl.class);

    public KeycloakAdminServicesAwsImpl(RealmAdminClientRepository realmAdminClientRepository, SecretsManagerClient secretsManagerClient) {
        super(realmAdminClientRepository);
        this.secretsManagerClient = secretsManagerClient;
    }

    @Override
    public void criarClientCredentials(String realm, KeycloakClientModelDto keycloakClientModelDto) {

        ClientRepresentation clientRepresentation = createClientCredentialKeycloak(realm, keycloakClientModelDto);

        createSecret(realm, keycloakClientModelDto, clientRepresentation);

        logger.info("Client credentials criado com sucesso");
    }

    private void createSecret(String realm, KeycloakClientModelDto keycloakClientModelDto, ClientRepresentation clientRepresentation) {
        String newSecretName = String.format(SECRET_NAME, realm, keycloakClientModelDto.clientId());
        CreateSecretRequest secretRequest = CreateSecretRequest.builder()
                .name(newSecretName)
                .secretString(String.format(CREDENTIAL_SECRET_VALUE, keycloakClientModelDto.clientId(), clientRepresentation.getSecret()))
                .build();
        try {
            secretsManagerClient.createSecret(secretRequest);

        } catch (ResourceExistsException e) {
            logger.error("Erro ao criar secret no Secrets Manager", e);
            throw new DomainException(String.format("Secret com nome %s já existe no Secrets Manager", newSecretName), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            logger.error("Erro ao criar secret no Secrets Manager", e);
            throw new DomainException(String.format("Erro ao criar secret no Secrets Manager: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
