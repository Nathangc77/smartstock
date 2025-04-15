package com.moreira.smartstock.tests.factories;

import com.moreira.smartstock.entities.Client;

public class ClientFactory {

    public static Client createCustomClient(Long clientId, String clientName) {
        Client client = new Client();
        client.setId(clientId);
        client.setName(clientName);
        return client;
    }
}
