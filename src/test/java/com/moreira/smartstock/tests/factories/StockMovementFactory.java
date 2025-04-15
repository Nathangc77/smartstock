package com.moreira.smartstock.tests.factories;

import com.moreira.smartstock.dtos.EntryMovementDTO;
import com.moreira.smartstock.dtos.ExitMovementDTO;
import com.moreira.smartstock.entities.StockMovement;
import com.moreira.smartstock.entities.TypeMovement;

import java.time.Instant;

public class StockMovementFactory {

    public static StockMovement createEntryStockMovement(Long stockMovementId, Long productId, Long providerId, String username) {
        StockMovement stockMovement =
                new StockMovement(stockMovementId, Instant.parse("2024-03-01T10:15:00Z"), 50, TypeMovement.ENTRADA, "Reposição de estoque");
        stockMovement.setUser(UserFactory.createCustomUser(username));
        stockMovement.setProduct(ProductFactory.createProduct(productId));
        stockMovement.setProvider(ProviderFactory.createProvider(providerId));
        return stockMovement;
    }

    public static StockMovement createExitStockMovement(Long stockMovementId, Long productId, Long clientId, String username) {
        StockMovement stockMovement =
                new StockMovement(stockMovementId, Instant.parse("2024-03-01T10:15:00Z"), 50, TypeMovement.SAIDA, "Venda de material");
        stockMovement.setUser(UserFactory.createCustomUser(username));
        stockMovement.setProduct(ProductFactory.createProduct(productId));
        stockMovement.setClient(ClientFactory.createCustomClient(clientId, "João Silva"));
        return stockMovement;
    }

    public static EntryMovementDTO createEntryMovement(Long productId, Long providerId) {
        return new EntryMovementDTO(50, "Reposição de estoque", productId, providerId);
    }

    public static ExitMovementDTO createExitMovement(Long productId, Long clientId) {
        return new ExitMovementDTO(50, "Venda de material", productId, clientId);
    }
}
