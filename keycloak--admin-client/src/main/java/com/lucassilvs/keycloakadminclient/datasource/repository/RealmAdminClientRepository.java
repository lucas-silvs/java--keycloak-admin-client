package com.lucassilvs.keycloakadminclient.datasource.repository;

import com.lucassilvs.keycloakadminclient.datasource.entity.RealmAdminClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RealmAdminClientRepository extends JpaRepository<RealmAdminClientEntity, UUID> {
    Optional<RealmAdminClientEntity> findByRealm(String realm);

    boolean existsByRealm(String realm);
}
