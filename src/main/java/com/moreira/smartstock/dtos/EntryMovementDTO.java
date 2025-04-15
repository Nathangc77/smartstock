package com.moreira.smartstock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class EntryMovementDTO {

    @NotNull(message = "Campo requerido")
    @Positive(message = "Quantidade precisa ser maior que 0")
    private Integer quantity;
    @NotBlank(message = "Campo requerido")
    @Size(min = 5, max = 80, message = "mensagem precisa conter entre 5 e 80 caracteres")
    private String reason;
    @NotNull(message = "Campo requerido")
    private Long productId;
    @NotNull(message = "Campo requerido")
    private Long providerId;

    public EntryMovementDTO() {
    }

    public EntryMovementDTO(Integer quantity, String reason, Long productId, Long providerId) {
        this.quantity = quantity;
        this.reason = reason;
        this.productId = productId;
        this.providerId = providerId;
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

    public Long getProviderId() {
        return providerId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
}
