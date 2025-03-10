package com.moreira.smartstock.dtos;

import com.moreira.smartstock.entities.Product;
import com.moreira.smartstock.entities.UnitMeasure;

public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private UnitMeasure unitMeasure;

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
        unitMeasure = entity.getUnitMeasure();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }
}
