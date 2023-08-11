package com.lucassilvs.keycloakadminclient.infrastructure.configuration.aws.secret;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;

@Component
public class SecretManagerClientComponents {


    private final SecretsManagerClient secretsManagerClient;

    @Autowired
    public SecretManagerClientComponents(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }

    public Map<String,String> getSecretValueByName(String secretName){
        GetSecretValueResponse response = secretsManagerClient.getSecretValue(GetSecretValueRequest.builder().secretId(secretName).build());

        return  new Gson().fromJson(response.secretString(), Map.class);
    }

}
