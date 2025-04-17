package com.moreira.smartstock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductSaveDTO {

    @Size(min = 3, max = 80, message = "Nome deve ter de 3 a 80 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;
    @NotNull(message = "Campo requerido")
    @Positive(message = "Preço deve ser maior que 0")
    private Double price;
    @NotBlank(message = "Campo requerido")
    @Size(min = 2, max = 2, message = "Unidade de medida precisa ter 2 caracteres")
    private String unitMeasure;
    @NotNull(message = "Campo requerido")
    private Long categoryId;

    public ProductSaveDTO() {
    }

    public ProductSaveDTO(String name, Double price, String unitMeasure, Long categoryId) {
        this.name = name;
        this.price = price;
        this.unitMeasure = unitMeasure;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
