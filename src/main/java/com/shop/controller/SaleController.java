package com.shop.controller;

import com.shop.dto.SaleRequestDto;
import com.shop.dto.SaleResponseDto;
import com.shop.service.SaleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Endpoints for managing sales transactions")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'CASHIER')")
    public ResponseEntity<SaleResponseDto> createSale(@Valid @RequestBody SaleRequestDto saleRequestDto) {
        SaleResponseDto newSale = saleService.createSale(saleRequestDto);
        return new ResponseEntity<>(newSale, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<List<SaleResponseDto>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<SaleResponseDto> getSaleById(@PathVariable Long id) {
        SaleResponseDto sale = saleService.getSaleById(id);
        return ResponseEntity.ok(sale);
    }
}
