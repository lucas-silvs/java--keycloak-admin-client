package com.lucassilvs.keycloakadminclient.controller.mapper;

import com.lucassilvs.keycloakadminclient.controller.dto.ClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.services.model.ClientCredential;
import org.keycloak.representations.idm.ClientRepresentation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientCredentialMapper {

    ClientCredentialMapper INSTANCE = Mappers.getMapper(ClientCredentialMapper.class);

    ClientCredential map(ClientCredentialDTO clientCredentialDTO);

    ClientCredential map(ClientRepresentation clientRepresentation);

    @Mapping(target = "clientSecret", ignore = true)
    @InheritInverseConfiguration
    ClientCredentialDTO map(ClientCredential clientCredential);

}
