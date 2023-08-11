package com.lucassilvs.keycloakadminclient.domain.ports.repositories;

import com.lucassilvs.keycloakadminclient.domain.model.AdminClientCredential;

import java.util.List;

public interface AdminClientCredentialRepository {

    AdminClientCredential save(AdminClientCredential clientCredential);

    AdminClientCredential findByRealm(String realm);

    boolean existsByRealm(String realm);

    List<AdminClientCredential> findAll();

}
