package com.moreira.smartstock.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moreira.smartstock.entities.*;

import java.time.Instant;

public class StockMovementDTO {

    private Long id;
    private Instant moment;
    private Integer quantity;
    private String type;
    private String reason;
    private ProductMinDTO product;
    private UserMinDTO user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProviderMinDTO provider;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientMinDTO client;

    public StockMovementDTO() {
    }

    public StockMovementDTO(Long id, Instant moment, Integer quantity, String type, String reason) {
        this.id = id;
        this.moment = moment;
        this.quantity = quantity;
        this.type = type;
        this.reason = reason;
    }

    public StockMovementDTO(StockMovement entity) {
        id = entity.getId();
        moment = entity.getMoment();
        quantity = entity.getQuantity();
        type = entity.getType().name();
        reason = entity.getReason();
        setProduct(entity.getProduct());
        setUser(entity.getUser());
        setProvider(entity.getProvider());
        setClient(entity.getClient());
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

    public ProductMinDTO getProduct() {
        return product;
    }

    public UserMinDTO getUser() {
        return user;
    }

    public ProviderMinDTO getProvider() {
        return provider;
    }

    public ClientMinDTO getClient() {
        return client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setProduct(Product product) {
        if (product != null)
            this.product = new ProductMinDTO(product);
    }

    public void setUser(User user) {
        if (user != null)
            this.user = new UserMinDTO(user);
    }

    public void setProvider(Provider provider) {
        if (provider != null)
            this.provider = new ProviderMinDTO(provider);
    }

    public void setClient(Client client) {
        if (client != null)
            this.client = new ClientMinDTO(client);
    }
}
