package com.shop.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class SaleResponseDto {
    private Long id;
    private String invoiceNumber;
    private Instant saleDate;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal netAmount;
    private UserResponseDto user;
    private List<SaleItemResponseDto> saleItems;
    private List<PaymentResponseDto> payments;
}
