package com.lucassilvs.keycloakadminclient.controller.mapper;

import com.lucassilvs.keycloakadminclient.controller.dto.RealmRoleDTO;
import com.lucassilvs.keycloakadminclient.services.model.RealmRole;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RealmRoleMapper {

    RealmRoleMapper INSTANCE = Mappers.getMapper(RealmRoleMapper.class);

    RealmRole map(RealmRoleDTO realmRoleDTO);

    @InheritInverseConfiguration
    RealmRoleDTO map(RealmRole realmRole);

}
