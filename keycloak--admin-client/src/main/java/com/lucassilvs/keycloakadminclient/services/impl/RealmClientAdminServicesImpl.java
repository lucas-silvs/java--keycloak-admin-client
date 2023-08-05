package com.lucassilvs.keycloakadminclient.services.impl;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.ApplicationException;
import com.lucassilvs.keycloakadminclient.configuration.exceptions.DomainException;
import com.lucassilvs.keycloakadminclient.controller.dto.RealmAdminClientDTO;
import com.lucassilvs.keycloakadminclient.datasource.entity.RealmAdminClientEntity;
import com.lucassilvs.keycloakadminclient.datasource.repository.RealmAdminClientRepository;
import com.lucassilvs.keycloakadminclient.services.RealmClientAdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RealmClientAdminServicesImpl implements RealmClientAdminServices {
    private final RealmAdminClientRepository realmAdminClientRepository;

    @Autowired
    public RealmClientAdminServicesImpl(RealmAdminClientRepository realmAdminClientRepository) {
        this.realmAdminClientRepository = realmAdminClientRepository;
    }

    @Override
    public void cadastrarRealmAdminClient(RealmAdminClientDTO keyclokaAdminClientDto) {

        if (realmAdminClientRepository.existsByRealm(keyclokaAdminClientDto.realm()))
            throw new ApplicationException(String.format("RealmAdminClient já cadastrado para o realm %s", keyclokaAdminClientDto.realm()), HttpStatus.CONFLICT);

        RealmAdminClientEntity realmAdminClientEntity = new RealmAdminClientEntity(keyclokaAdminClientDto);
        try {
            realmAdminClientRepository.save(realmAdminClientEntity);
        }catch (Exception e) {
            throw new DomainException(String.format("Erro ao cadastrar RealmAdminClient: %s", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public RealmAdminClientDTO buscarRealmAdminClient(String realm) {
        Optional<RealmAdminClientEntity> optionalRealmAdminClientEntity = realmAdminClientRepository.findByRealm(realm);

        return optionalRealmAdminClientEntity.orElseThrow( () -> new ApplicationException("RealmAdminClient não encontrado", HttpStatus.NOT_FOUND)).toRealmAdminClientDTO();
    }

    @Override
    public List<RealmAdminClientDTO> listarRealmAdminClients() {
        List<RealmAdminClientEntity> listRealmAdminClientEntity = realmAdminClientRepository.findAll();

       return listRealmAdminClientEntity
                .stream()
                .map(RealmAdminClientEntity::toRealmAdminClientDTO)
                .collect(Collectors.toList());
    }
}
