package com.moreira.smartstock.dtos;

import com.moreira.smartstock.entities.Product;

public record ProductMinDTO(Long id, String name) {

    public ProductMinDTO(Product product) {
        this(product.getId(), product.getName());
    }
}
