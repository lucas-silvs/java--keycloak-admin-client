package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminClientCredentialDTO(
        @NotBlank
        String realm,
        @NotBlank
        String clientId,

        @NotBlank
        String clientSecret,

        @NotBlank
        String grantType,
        @NotBlank
        String serverUrl
){

    public AdminClientCredentialDTO(String realm,
                                    String clientId,
                                    String clientSecret,
                                    String grantType,
                                    String serverUrl) {
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.serverUrl = serverUrl;
    }

    public AdminClientCredentialDTO(String realm, String clientId, String grantType, String serverUrl) {
        this(realm, clientId, null, grantType, serverUrl);
    }
}
