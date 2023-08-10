package com.lucassilvs.keycloakadminclient.controller.mapper;

import com.lucassilvs.keycloakadminclient.controller.dto.ClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.services.model.ClientCredential;
import com.lucassilvs.keycloakadminclient.services.model.RealmRole;
import org.keycloak.representations.idm.ClientRepresentation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientCredentialMapper {

    ClientCredentialMapper INSTANCE = Mappers.getMapper(ClientCredentialMapper.class);

    @Mapping(target = "roleIamName", source = "clientCredentialDTO.roleIamName")
    ClientCredential map(ClientCredentialDTO clientCredentialDTO);

    ClientCredential map(ClientRepresentation clientRepresentation);

    @Mapping(target = "clientSecret", ignore = true)
    @InheritInverseConfiguration
    ClientCredentialDTO map(ClientCredential clientCredential);


    @Mapping(source = "listRoles", target = "realmRoles")
    ClientCredential map(ClientRepresentation client, List<RealmRole> listRoles);
}
