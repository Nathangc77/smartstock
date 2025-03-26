package com.moreira.smartstock.dtos;

import com.moreira.smartstock.entities.StockMovement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class StockMovementDTO {

    private Long id;
    private Instant moment;
    @NotNull(message = "Campo requerido")
    @Positive(message = "Quantidade deve ser maior que 0")
    private Integer quantity;
    @NotBlank(message = "Campo requerido")
    private String type;
    @NotBlank(message = "Campo requerido")
    @Size(min = 5, max = 100, message = "mensagem deve conter entre 5 e 100 caracteres")
    private String reason;
    @NotNull(message = "Campo requerido")
    private Long productId;
    private Long userId;

    public StockMovementDTO() {
    }

    public StockMovementDTO(Long id, Instant moment, Integer quantity, String type, String reason, Long productId, Long userId) {
        this.id = id;
        this.moment = moment;
        this.quantity = quantity;
        this.type = type;
        this.reason = reason;
        this.productId = productId;
        this.userId = userId;
    }

    public StockMovementDTO(StockMovement entity) {
        id = entity.getId();
        moment = entity.getMoment();
        quantity = entity.getQuantity();
        type = entity.getType().name();
        reason = entity.getReason();
        productId = entity.getProduct().getId();
        userId = entity.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }
}
