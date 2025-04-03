package com.moreira.smartstock.dtos;

import com.moreira.smartstock.entities.Provider;

public record ProviderMinDTO(Long id, String name, String cnpj) {

    public ProviderMinDTO(Provider provider) {
        this(provider.getId(), provider.getName(), provider.getCnpj());
    }
}
