package com.lucassilvs.keycloakadminclient.application.controller.mapper;

import com.lucassilvs.keycloakadminclient.domain.model.AdminClientCredential;
import com.lucassilvs.keycloakadminclient.infrastructure.datasources.entity.AdminClientCredentialEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminClientCredentialEntityMapper {

    AdminClientCredentialEntityMapper INSTANCE = Mappers.getMapper(AdminClientCredentialEntityMapper.class);
    AdminClientCredentialEntity map(AdminClientCredential adminClientCredentials);

    @InheritInverseConfiguration
    AdminClientCredential map(AdminClientCredentialEntity adminClientCredentialEntity);
}
