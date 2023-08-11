package com.lucassilvs.keycloakadminclient.infrastructure.configuration;

import com.lucassilvs.keycloakadminclient.domain.adapters.services.ClientCredentialPortsImpl;
import com.lucassilvs.keycloakadminclient.domain.adapters.services.RealmClientAdminPortImpl;
import com.lucassilvs.keycloakadminclient.domain.ports.interfaces.ClientCredentialPorts;
import com.lucassilvs.keycloakadminclient.domain.ports.interfaces.RealmClientAdminPort;
import com.lucassilvs.keycloakadminclient.domain.ports.repositories.AdminClientCredentialRepository;
import com.lucassilvs.keycloakadminclient.domain.ports.repositories.OidcProviderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfiguration {

    @Bean
    public RealmClientAdminPort realmClientAdminServices(AdminClientCredentialRepository adminClientCredentialRepository){
        return new RealmClientAdminPortImpl(adminClientCredentialRepository);
    }

    @Bean
    public ClientCredentialPorts clientCredentialPorts(OidcProviderClient oidcProviderClient){
        return new ClientCredentialPortsImpl(oidcProviderClient);
    }
}
