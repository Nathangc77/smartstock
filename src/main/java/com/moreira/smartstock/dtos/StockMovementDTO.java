package com.moreira.smartstock.dtos;

import com.moreira.smartstock.entities.StockMovement;

import java.time.Instant;

public class StockMovementDTO {

    private Long id;
    private Instant moment;
    private Integer quantity;
    private String type;
    private String reason;
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
