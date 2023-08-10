package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.configuration.aws.role.AwsRoleIamClientComponent;
import com.lucassilvs.keycloakadminclient.configuration.exceptions.DomainException;
import com.lucassilvs.keycloakadminclient.datasource.repository.RealmAdminClientRepository;
import com.lucassilvs.keycloakadminclient.services.model.ClientCredential;
import org.keycloak.representations.idm.ClientRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretResponse;
import software.amazon.awssdk.services.secretsmanager.model.ResourceExistsException;

@Service
@Profile("localstack")
public class KeycloakAdminServicesAwsImpl extends KeycloakAdminServicesImpl {


    private final SecretsManagerClient secretsManagerClient;
    private final AwsRoleIamClientComponent roleIamClientComponent;
    private static final String CREDENTIAL_SECRET_VALUE = "{ \"client-id\": \"%s\", \"client-secret\": \"%s\" }";
    private static final String SECRET_NAME = "keycloak/%s/%s";
    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminServicesAwsImpl.class);

    @Autowired
    public KeycloakAdminServicesAwsImpl(RealmAdminClientRepository realmAdminClientRepository, SecretsManagerClient secretsManagerClient, AwsRoleIamClientComponent roleIamClientComponent) {
        super(realmAdminClientRepository);
        this.secretsManagerClient = secretsManagerClient;
        this.roleIamClientComponent = roleIamClientComponent;
    }

    @Override
    public void criarClientCredentials(String realm, ClientCredential clientCredential) {
        String newSecretName = String.format(SECRET_NAME, realm, clientCredential.clientId());

        ClientRepresentation clientRepresentation = createClientCredentialKeycloak(realm, clientCredential);
        CreateSecretResponse secretResponse = createSecret(newSecretName, clientCredential, clientRepresentation);
        roleIamClientComponent.addpolicySecretKeycloakCredentials(clientCredential.roleIamName(), secretResponse.arn(), newSecretName);

    }

    private CreateSecretResponse createSecret(String newSecretName, ClientCredential clientCredential, ClientRepresentation clientRepresentation) {
        CreateSecretRequest secretRequest = CreateSecretRequest.builder()
                .name(newSecretName)
                .secretString(String.format(CREDENTIAL_SECRET_VALUE, clientCredential.clientId(), clientRepresentation.getSecret()))
                .build();
        try {
            return secretsManagerClient.createSecret(secretRequest);

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
