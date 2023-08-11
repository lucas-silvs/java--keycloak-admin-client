package com.lucassilvs.keycloakadminclient.domain.model;

import java.util.List;

public record ClientCredential (

        String clientId,
        String clientSecret,
        List<RealmRole> realmRoles,
        String roleIamName

) {
}
