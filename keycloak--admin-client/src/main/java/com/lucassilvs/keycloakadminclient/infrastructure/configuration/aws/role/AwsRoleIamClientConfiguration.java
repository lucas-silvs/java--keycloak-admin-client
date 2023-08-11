package com.lucassilvs.keycloakadminclient.infrastructure.configuration.aws.role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.iam.IamClient;

@Component
public class AwsRoleIamClientConfiguration {

    @Bean
    @Profile("localstack")
    public IamClient iamClientLocalstack(){
        return IamClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .endpointOverride(java.net.URI.create("http://localhost:4566"))
                .build();
    }

    @Bean
    @Profile("aws")
    public IamClient iamClientAWS(){
        return IamClient.builder().build();
    }
}
