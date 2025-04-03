package com.moreira.smartstock.dtos;

import com.moreira.smartstock.entities.Client;

public record ClientMinDTO(Long id, String name, String cpfCnpj) {

    public ClientMinDTO(Client client) {
        this(client.getId(), client.getName(), client.getCpfCnpj());
    }
}
