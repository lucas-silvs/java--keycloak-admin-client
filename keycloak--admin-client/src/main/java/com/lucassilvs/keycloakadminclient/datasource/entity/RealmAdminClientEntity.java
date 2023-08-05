package com.lucassilvs.keycloakadminclient.datasource.entity;

import com.lucassilvs.keycloakadminclient.controller.dto.RealmAdminClientDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "realm_admin_client_credentials")
public class RealmAdminClientEntity {

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

    public RealmAdminClientEntity() {
    }

    public RealmAdminClientEntity(RealmAdminClientDTO keyclokaAdminClientDto) {
        this.setRealm(keyclokaAdminClientDto.realm());
        this.setClientId(keyclokaAdminClientDto.clientId());
        this.setClientSecret(keyclokaAdminClientDto.clientSecret());
        this.setGrantType(keyclokaAdminClientDto.grantType());
        this.setServerUrl(keyclokaAdminClientDto.serverUrl());
    }


    public RealmAdminClientEntity(String realm, String clientId, String clientSecret, String grantType, String serverUrl) {
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.serverUrl = serverUrl;
    }

    public RealmAdminClientDTO toRealmAdminClientDTO() {
        return new RealmAdminClientDTO(
                this.getRealm(),
                this.getClientId(),
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
