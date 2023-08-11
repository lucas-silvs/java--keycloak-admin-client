package com.lucassilvs.keycloakadminclient.infrastructure.configuration.aws.secret;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Component
public class SecretManagerClientConfiguration {

    @Bean
    @Profile("aws")
    public SecretsManagerClient secretsManagerClientAWS(){
        return SecretsManagerClient.create();
    }

    @Bean
    @Profile("localstack")
    public SecretsManagerClient secretsManagerClientLocalstack(){
        return SecretsManagerClient.builder()
                .endpointOverride(java.net.URI.create("http://localhost:4566"))
                .build();
    }
}
