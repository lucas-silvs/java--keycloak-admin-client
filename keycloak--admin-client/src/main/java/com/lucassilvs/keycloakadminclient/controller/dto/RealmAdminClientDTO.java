package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

public record RealmAdminClientDTO (
        @Nonnull
        String realm,
        @Nonnull
        String clientId,
        String clientSecret,
        String grantType,
        @Nonnull
        String serverUrl
){

    public RealmAdminClientDTO(String realm,
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

    public RealmAdminClientDTO(@Nonnull String realm, @Nonnull String clientId, String grantType, @Nonnull String serverUrl) {
        this(realm, clientId, null, grantType, serverUrl);
    }
}
