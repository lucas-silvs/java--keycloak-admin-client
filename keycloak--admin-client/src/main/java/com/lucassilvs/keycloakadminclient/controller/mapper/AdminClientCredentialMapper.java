package com.lucassilvs.keycloakadminclient.controller.mapper;

import com.lucassilvs.keycloakadminclient.controller.dto.AdminClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.services.model.AdminClientCredentials;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminClientCredentialMapper {

    AdminClientCredentialMapper INSTANCE = Mappers.getMapper(AdminClientCredentialMapper.class);

    AdminClientCredentials map(AdminClientCredentialDTO adminClientCredentialDTO);


    @Mapping(target = "clientSecret", ignore = true)
    @InheritInverseConfiguration
    AdminClientCredentialDTO map(AdminClientCredentials adminClientCredentials);

}
