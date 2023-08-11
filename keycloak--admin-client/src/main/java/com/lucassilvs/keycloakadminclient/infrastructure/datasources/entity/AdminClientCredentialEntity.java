package com.lucassilvs.keycloakadminclient.infrastructure.datasources.entity;

import com.lucassilvs.keycloakadminclient.domain.model.AdminClientCredential;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "realm_admin_client_credentials")
public class AdminClientCredentialEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(unique = true)
    private String realm;
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String serverUrl;

    public AdminClientCredentialEntity() {
    }

    public AdminClientCredentialEntity(AdminClientCredential adminClientCredentials) {
        this.setRealm(adminClientCredentials.realm());
        this.setClientId(adminClientCredentials.clientId());
        this.setClientSecret(adminClientCredentials.clientSecret());
        this.setGrantType(adminClientCredentials.grantType());
        this.setServerUrl(adminClientCredentials.serverUrl());
    }


    public AdminClientCredentialEntity(String realm, String clientId, String clientSecret, String grantType, String serverUrl) {
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.serverUrl = serverUrl;
    }

    public AdminClientCredential toAdminClientCredentials() {
        return new AdminClientCredential(
                this.getRealm(),
                this.getClientId(),
                null,
                this.getGrantType(),
                this.getServerUrl()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
