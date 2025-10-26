package com.shop.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal sellingPrice;
    private String categoryName;
}