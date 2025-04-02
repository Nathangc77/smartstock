package com.moreira.smartstock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ExitMovementDTO {

    @NotNull(message = "Campo requerido")
    @Positive(message = "Quantidade precisa ser maior que 0")
    private Integer quantity;
    @NotBlank(message = "Campo requerido")
    @Size(min = 5, max = 80, message = "mensagem precisa conter entre 5 e 80 caracteres")
    private String reason;
    @NotNull(message = "Campo requerido")
    private Long productId;
    @NotNull(message = "Campo requerido")
    private Long clientId;

    public ExitMovementDTO() {
    }

    public ExitMovementDTO(Integer quantity, String reason, Long productId, Long clientId) {
        this.quantity = quantity;
        this.reason = reason;
        this.productId = productId;
        this.clientId = clientId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getReason() {
        return reason;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getClientId() {
        return clientId;
    }
}
