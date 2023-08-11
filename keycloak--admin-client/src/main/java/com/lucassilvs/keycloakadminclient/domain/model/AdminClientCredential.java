package com.lucassilvs.keycloakadminclient.domain.model;

public record AdminClientCredential(
        String realm,
        String clientId,
        String clientSecret,
        String grantType,
        String serverUrl
) {
}
