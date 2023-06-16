package com.lucassilvs.keycloakadminclient.configuration.keycloak;

import com.lucassilvs.keycloakadminclient.configuration.keycloak.properties.KeycloakProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {

    private final KeycloakProperties keycloakProperties;

    public KeycloakConfiguration(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .grantType(keycloakProperties.getGrantType())
                .realm(keycloakProperties.getRealm())
                .serverUrl(keycloakProperties.getServerUrl())
                .build();
    }


}
