package com.moreira.smartstock.controllers;

import com.moreira.smartstock.dtos.EntryMovementDTO;
import com.moreira.smartstock.dtos.ExitMovementDTO;
import com.moreira.smartstock.dtos.StockMovementDTO;
import com.moreira.smartstock.services.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/movements")
public class StockMovementController {

    @Autowired
    private StockMovementService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    public ResponseEntity<Page<StockMovementDTO>> findAll(Pageable pageable) {
        Page<StockMovementDTO> result = service.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/entry")
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    public ResponseEntity<StockMovementDTO> registerStockEntry(@Valid @RequestBody EntryMovementDTO dto) {
        StockMovementDTO result = service.registerStockEntry(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/movements").buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(result);
    }

    @PostMapping(value = "/exit")
    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_ADMIN')")
    public ResponseEntity<StockMovementDTO> registerStockExit(@Valid @RequestBody ExitMovementDTO dto) {
        StockMovementDTO result = service.registerStockExit(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/movements").buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(result);
    }
}
