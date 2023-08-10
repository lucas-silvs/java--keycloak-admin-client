package com.lucassilvs.keycloakadminclient.services.model;

import java.util.List;

public record ClientCredential (

        String clientId,
        String clientSecret,
        List<RealmRole> realmRoles,
        String roleIamName

) {
}
