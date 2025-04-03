package com.moreira.smartstock.repositories;

import com.moreira.smartstock.entities.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    @EntityGraph(attributePaths = {"product", "user", "provider", "client"})
    @Query(value = "SELECT obj FROM StockMovement obj")
    Page<StockMovement> searchStockMovement(Pageable pageable);
}
