package com.moreira.smartstock.controllers;

import com.moreira.smartstock.dtos.StockMovementDTO;
import com.moreira.smartstock.services.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movements")
public class StockMovementController {

    @Autowired
    private StockMovementService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    public ResponseEntity<StockMovementDTO> movement(@RequestBody StockMovementDTO dto) {
        dto = service.movement(dto);
        return ResponseEntity.ok(dto);
    }
}
