package com.lucassilvs.keycloakadminclient.infrastructure.datasources.repositories;

import com.lucassilvs.keycloakadminclient.infrastructure.datasources.entity.AdminClientCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RealmAdminClientJPARepository extends JpaRepository<AdminClientCredentialEntity, UUID> {
    Optional<AdminClientCredentialEntity> findByRealm(String realm);

    boolean existsByRealm(String realm);
}
