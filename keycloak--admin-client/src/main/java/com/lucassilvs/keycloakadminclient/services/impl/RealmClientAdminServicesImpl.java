package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.ApplicationException;
import com.lucassilvs.keycloakadminclient.configuration.exceptions.DomainException;
import com.lucassilvs.keycloakadminclient.controller.mapper.AdminClientCredentialEntityMapper;
import com.lucassilvs.keycloakadminclient.datasource.entity.AdminClientCredentialEntity;
import com.lucassilvs.keycloakadminclient.datasource.repository.RealmAdminClientRepository;
import com.lucassilvs.keycloakadminclient.services.RealmClientAdminServices;
import com.lucassilvs.keycloakadminclient.services.model.AdminClientCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RealmClientAdminServicesImpl implements RealmClientAdminServices {
    private final RealmAdminClientRepository realmAdminClientRepository;

    private final AdminClientCredentialEntityMapper adminClientCredentialEntityMapper = AdminClientCredentialEntityMapper.INSTANCE;

    @Autowired
    public RealmClientAdminServicesImpl(RealmAdminClientRepository realmAdminClientRepository) {
        this.realmAdminClientRepository = realmAdminClientRepository;
    }

    @Override
    public void cadastrarRealmAdminClient(AdminClientCredentials adminClientCredential) {

        if (realmAdminClientRepository.existsByRealm(adminClientCredential.realm()))
            throw new ApplicationException(String.format("RealmAdminClient já cadastrado para o realm %s", adminClientCredential.realm()), HttpStatus.CONFLICT);

        AdminClientCredentialEntity adminClientCredentialEntity = adminClientCredentialEntityMapper.map(adminClientCredential);
        try {
            realmAdminClientRepository.save(adminClientCredentialEntity);
        }catch (Exception e) {
            throw new DomainException(String.format("Erro ao cadastrar RealmAdminClient: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public AdminClientCredentials buscarRealmAdminClient(String realm) {
        Optional<AdminClientCredentialEntity> optionalRealmAdminClientEntity = realmAdminClientRepository.findByRealm(realm);

        return adminClientCredentialEntityMapper.map(optionalRealmAdminClientEntity.orElseThrow( () -> new ApplicationException("RealmAdminClient não encontrado", HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<AdminClientCredentials> listarRealmAdminClients() {
        List<AdminClientCredentialEntity> listAdminClientCredentialEntity = realmAdminClientRepository.findAll();

       return listAdminClientCredentialEntity
                .stream()
                .map(adminClientCredentialEntityMapper::map)
                .collect(Collectors.toList());
    }
}
