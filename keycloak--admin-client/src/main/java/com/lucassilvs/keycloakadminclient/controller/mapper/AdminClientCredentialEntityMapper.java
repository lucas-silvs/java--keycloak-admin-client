package com.lucassilvs.keycloakadminclient.controller.mapper;

import com.lucassilvs.keycloakadminclient.datasource.entity.AdminClientCredentialEntity;
import com.lucassilvs.keycloakadminclient.services.model.AdminClientCredentials;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminClientCredentialEntityMapper {

    AdminClientCredentialEntityMapper INSTANCE = Mappers.getMapper(AdminClientCredentialEntityMapper.class);
    AdminClientCredentialEntity map(AdminClientCredentials adminClientCredentials);

    @InheritInverseConfiguration
    AdminClientCredentials map(AdminClientCredentialEntity adminClientCredentialEntity);
}
