package com.moreira.smartstock.services;

import com.moreira.smartstock.dtos.StockMovementDTO;
import com.moreira.smartstock.entities.StockMovement;
import com.moreira.smartstock.repositories.ProductRepository;
import com.moreira.smartstock.repositories.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockMovementService {

    @Autowired
    private StockMovementRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Page<StockMovementDTO> findAll(Pageable pageable) {
        Page<StockMovement> result = repository.findAll(pageable);
        return result.map(StockMovementDTO::new);
    }

}
