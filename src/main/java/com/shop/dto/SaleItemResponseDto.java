package com.shop.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SaleItemResponseDto {
    private Long id;
    private ProductResponseDto product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}