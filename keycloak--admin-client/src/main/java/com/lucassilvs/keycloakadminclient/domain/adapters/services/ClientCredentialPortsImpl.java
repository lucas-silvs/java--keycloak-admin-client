package com.lucassilvs.keycloakadminclient.domain.adapters.services;

import com.lucassilvs.keycloakadminclient.application.exceptions.DomainException;
import com.lucassilvs.keycloakadminclient.domain.model.ClientCredential;
import com.lucassilvs.keycloakadminclient.domain.model.RealmRole;
import com.lucassilvs.keycloakadminclient.domain.ports.interfaces.ClientCredentialPorts;
import com.lucassilvs.keycloakadminclient.domain.ports.repositories.OidcProviderClient;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class ClientCredentialPortsImpl implements ClientCredentialPorts {

    private final OidcProviderClient oidcProviderClient;

    public ClientCredentialPortsImpl(OidcProviderClient oidcProviderClient) {
        this.oidcProviderClient = oidcProviderClient;
    }

    @Override
    public void criarClientCredentials(String realm, ClientCredential clientCredential) {
        oidcProviderClient.createClientCredential(realm, clientCredential);
    }

    public ClientCredential buscarClientCredentials(String realm, String clientId) {
            Optional<ClientCredential> optionalClientCredential = oidcProviderClient.getClientCredential(realm, clientId);

            if (optionalClientCredential.isPresent()) {
                return optionalClientCredential.get();
            }

            throw new DomainException("Client Credential não encontrado", HttpStatus.NOT_FOUND);

    }

    @Override
    public void criarRealmRole(String realm, RealmRole keycloakRealmRoleModelDto) {
        oidcProviderClient.createRealmRole(realm, keycloakRealmRoleModelDto);
    }

    @Override
    public RealmRole buscaRealmRole(String realm, String nomeRole) {
        Optional<RealmRole> optionalRealmRole = oidcProviderClient.getRealmRole(realm, nomeRole);

        if (optionalRealmRole.isPresent()) {
            return optionalRealmRole.get();
        }
        throw new DomainException("Realm Role não encontrado", HttpStatus.NOT_FOUND);
    }

    @Override
    public void atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole) {
        oidcProviderClient.attachRealmRoleToClient(realm, clientId, nomeRole);
    }
}
