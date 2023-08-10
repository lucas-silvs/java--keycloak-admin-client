package com.lucassilvs.keycloakadminclient.controller.mapper;

import com.lucassilvs.keycloakadminclient.controller.dto.RealmRoleDTO;
import com.lucassilvs.keycloakadminclient.services.model.RealmRole;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface RealmRoleMapper {

    RealmRoleMapper INSTANCE = Mappers.getMapper(RealmRoleMapper.class);

    RealmRole map(RealmRoleDTO realmRoleDTO);

    @InheritInverseConfiguration
    RealmRoleDTO map(RealmRole realmRole);


    @Mapping(source = "attributes", target = "atributos")
    @Mapping(source = "roleRepresentation.description", target = "descricao")
    @Mapping(source = "roleRepresentation.name", target = "nome")
    RealmRole map(RoleRepresentation roleRepresentation, Map<String, List<String>> attributes);

}
