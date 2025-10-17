package com.shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "SKU cannot be blank")
    private String sku;

    private String description;

    @NotNull(message = "Cost price is required")
    @Min(value = 0, message = "Cost price must be positive")
    private BigDecimal costPrice;

    @NotNull(message = "Selling price is required")
    @Min(value = 0, message = "Selling price must be positive")
    private BigDecimal sellingPrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
