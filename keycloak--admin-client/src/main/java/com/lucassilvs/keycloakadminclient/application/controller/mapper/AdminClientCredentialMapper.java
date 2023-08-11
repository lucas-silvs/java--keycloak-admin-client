package com.lucassilvs.keycloakadminclient.application.controller.mapper;

import com.lucassilvs.keycloakadminclient.application.controller.dto.AdminClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.domain.model.AdminClientCredential;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminClientCredentialMapper {

    AdminClientCredentialMapper INSTANCE = Mappers.getMapper(AdminClientCredentialMapper.class);

    AdminClientCredential map(AdminClientCredentialDTO adminClientCredentialDTO);


    @Mapping(target = "clientSecret", ignore = true)
    @InheritInverseConfiguration
    AdminClientCredentialDTO map(AdminClientCredential adminClientCredentials);

}
