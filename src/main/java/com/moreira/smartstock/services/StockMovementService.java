package com.moreira.smartstock.services;

import com.moreira.smartstock.dtos.EntryMovementDTO;
import com.moreira.smartstock.dtos.ExitMovementDTO;
import com.moreira.smartstock.dtos.StockMovementDTO;
import com.moreira.smartstock.entities.*;
import com.moreira.smartstock.repositories.ClientRepository;
import com.moreira.smartstock.repositories.ProductRepository;
import com.moreira.smartstock.repositories.ProviderRepository;
import com.moreira.smartstock.repositories.StockMovementRepository;
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
    private ProviderRepository providerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Page<StockMovementDTO> findAll(Pageable pageable) {
        Page<StockMovement> result = repository.findAll(pageable);
        return result.map(StockMovementDTO::new);
    }

    @Transactional
    public StockMovementDTO registerStockEntry(EntryMovementDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));

        StockMovement entity = new StockMovement();
        entity.setMoment(Instant.now());
        entity.setQuantity(dto.getQuantity());
        entity.setReason(dto.getReason());
        entity.setType(TypeMovement.ENTRADA);
        entity.setProduct(product);
        entity.setProvider(provider);
        entity.setUser(userService.getUserLogged());

        entity = repository.save(entity);
        product.setQuantity(product.getQuantity() + dto.getQuantity());
        productRepository.save(product);

        return new StockMovementDTO(entity);
    }

    @Transactional
    public StockMovementDTO registerStockExit(ExitMovementDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (product.getQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Estoque insuficiente");
        }

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        StockMovement entity = new StockMovement();
        entity.setMoment(Instant.now());
        entity.setQuantity(dto.getQuantity());
        entity.setReason(dto.getReason());
        entity.setType(TypeMovement.SAIDA);
        entity.setProduct(product);
        entity.setClient(client);
        entity.setUser(userService.getUserLogged());

        entity = repository.save(entity);

        product.setQuantity(product.getQuantity() - dto.getQuantity());
        productRepository.save(product);

        return new StockMovementDTO(entity);
    }

}
