package com.lucassilvs.keycloakadminclient.domain.adapters.services;

import com.lucassilvs.keycloakadminclient.application.exceptions.ApplicationException;
import com.lucassilvs.keycloakadminclient.domain.model.AdminClientCredential;
import com.lucassilvs.keycloakadminclient.domain.ports.interfaces.RealmClientAdminPort;
import com.lucassilvs.keycloakadminclient.domain.ports.repositories.AdminClientCredentialRepository;
import org.springframework.http.HttpStatus;

import java.util.List;

public class RealmClientAdminPortImpl implements RealmClientAdminPort {

    private final AdminClientCredentialRepository adminClientCredentialRepository;

    public RealmClientAdminPortImpl(AdminClientCredentialRepository adminClientCredentialRepository) {
        this.adminClientCredentialRepository = adminClientCredentialRepository;
    }

    @Override
    public void cadastrarRealmAdminClient(AdminClientCredential adminClientCredential) {
        if (adminClientCredentialRepository.existsByRealm(adminClientCredential.realm()))
            throw new ApplicationException(String.format("RealmAdminClient já cadastrado para o realm %s", adminClientCredential.realm()), HttpStatus.CONFLICT);

        adminClientCredentialRepository.save(adminClientCredential);
    }

    @Override
    public AdminClientCredential buscarRealmAdminClient(String realm) {
        return adminClientCredentialRepository.findByRealm(realm);
    }

    @Override
    public List<AdminClientCredential> listarRealmAdminClients() {
        return adminClientCredentialRepository.findAll();
    }
}
