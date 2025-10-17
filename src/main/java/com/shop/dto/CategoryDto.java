package com.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private String description;
}
