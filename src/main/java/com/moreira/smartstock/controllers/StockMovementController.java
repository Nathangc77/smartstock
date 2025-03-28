package com.moreira.smartstock.controllers;

import com.moreira.smartstock.dtos.StockMovementDTO;
import com.moreira.smartstock.services.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movements")
public class StockMovementController {

    @Autowired
    private StockMovementService service;

    @GetMapping
    public ResponseEntity<Page<StockMovementDTO>> findAll(Pageable pageable) {
        Page<StockMovementDTO> result = service.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    public ResponseEntity<StockMovementDTO> movement(@RequestBody @Valid StockMovementDTO dto) {
        dto = service.movement(dto);
        return ResponseEntity.ok(dto);
    }
}
