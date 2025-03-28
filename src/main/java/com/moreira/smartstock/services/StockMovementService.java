package com.moreira.smartstock.services;

import com.moreira.smartstock.dtos.StockMovementDTO;
import com.moreira.smartstock.entities.Product;
import com.moreira.smartstock.entities.StockMovement;
import com.moreira.smartstock.entities.TypeMovement;
import com.moreira.smartstock.repositories.ProductRepository;
import com.moreira.smartstock.repositories.StockMovementRepository;
import com.moreira.smartstock.repositories.UserRepository;
import com.moreira.smartstock.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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

    @Transactional
    public StockMovementDTO movement(StockMovementDTO dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado")
        );

        if (dto.getType().equals(TypeMovement.ENTRADA.name())) {
            product.setQuantity(product.getQuantity() + dto.getQuantity());
        } else if (dto.getType().equals(TypeMovement.SAIDA.name())) {
            if (dto.getQuantity() > product.getQuantity()) throw new RuntimeException("Estoque insuficiente");
            product.setQuantity(product.getQuantity() - dto.getQuantity());
        } else {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        productRepository.save(product);

        StockMovement entity = new StockMovement();
        entity.setQuantity(dto.getQuantity());
        entity.setMoment(Instant.now());
        entity.setType(TypeMovement.valueOf(dto.getType()));
        entity.setReason(dto.getReason());
        entity.setProduct(product);
        entity.setUser(userService.getUserLogged());
        entity = repository.save(entity);
        return new StockMovementDTO(entity);
    }
}
