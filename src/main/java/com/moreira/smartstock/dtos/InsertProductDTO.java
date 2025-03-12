package com.moreira.smartstock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class InsertProductDTO {

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

    public InsertProductDTO() {
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
}
