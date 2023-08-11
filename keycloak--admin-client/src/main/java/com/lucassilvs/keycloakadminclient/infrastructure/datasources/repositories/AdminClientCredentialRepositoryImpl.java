package com.lucassilvs.keycloakadminclient.infrastructure.datasources.repositories;

import com.lucassilvs.keycloakadminclient.application.controller.mapper.AdminClientCredentialEntityMapper;
import com.lucassilvs.keycloakadminclient.application.exceptions.ApplicationException;
import com.lucassilvs.keycloakadminclient.application.exceptions.InfraestructureException;
import com.lucassilvs.keycloakadminclient.domain.model.AdminClientCredential;
import com.lucassilvs.keycloakadminclient.domain.ports.repositories.AdminClientCredentialRepository;
import com.lucassilvs.keycloakadminclient.infrastructure.datasources.entity.AdminClientCredentialEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdminClientCredentialRepositoryImpl implements AdminClientCredentialRepository {

    private final RealmAdminClientJPARepository realmAdminClientJPARepository;

    private final AdminClientCredentialEntityMapper adminClientCredentialEntityMapper = AdminClientCredentialEntityMapper.INSTANCE;


    public AdminClientCredentialRepositoryImpl(RealmAdminClientJPARepository realmAdminClientJPARepository) {
        this.realmAdminClientJPARepository = realmAdminClientJPARepository;
    }

    @Override
    public AdminClientCredential save(AdminClientCredential clientCredential) {

        AdminClientCredentialEntity adminClientCredentialEntity = adminClientCredentialEntityMapper.map(clientCredential);
        try {
            realmAdminClientJPARepository.save(adminClientCredentialEntity);
        }catch (Exception e) {
            throw new InfraestructureException(String.format("Erro ao cadastrar Client Credential de adminstrador: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return adminClientCredentialEntityMapper.map(adminClientCredentialEntity);
    }

    @Override
    public AdminClientCredential findByRealm(String realm) {
        Optional<AdminClientCredentialEntity> optionalRealmAdminClientEntity = realmAdminClientJPARepository.findByRealm(realm);

        return adminClientCredentialEntityMapper.map(optionalRealmAdminClientEntity.orElseThrow( () -> new ApplicationException("Client Credential de adminstrador de realm não encontrado", HttpStatus.NOT_FOUND)));
    }

    @Override
    public boolean existsByRealm(String realm) {
        return realmAdminClientJPARepository.existsByRealm(realm);
    }

    @Override
    public List<AdminClientCredential> findAll() {
        List<AdminClientCredentialEntity> listAdminClientCredentialEntity = realmAdminClientJPARepository.findAll();
        return listAdminClientCredentialEntity
                .stream()
                .map(adminClientCredentialEntityMapper::map)
                .collect(Collectors.toList());
    }
}
