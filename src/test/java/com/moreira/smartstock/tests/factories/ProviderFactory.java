package com.moreira.smartstock.tests.factories;

import com.moreira.smartstock.entities.Provider;

public class ProviderFactory {

    public static Provider createProvider(Long providerId) {
        return new Provider(providerId, "Rei dos parafusos", "59088941000171", "reidosparafusos@gmail.com", "79996854687", true);
    }
}
