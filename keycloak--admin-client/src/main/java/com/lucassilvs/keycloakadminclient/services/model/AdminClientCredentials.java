package com.lucassilvs.keycloakadminclient.services.model;

public record AdminClientCredentials(
        String realm,
        String clientId,
        String clientSecret,
        String grantType,
        String serverUrl
) {
}
