package com.shop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SaleRequestDto {
    private Long customerId; // Optional

    private BigDecimal discount = BigDecimal.ZERO;

    @NotEmpty(message = "Sale must contain at least one item")
    @Valid
    private List<SaleItemDto> items;

    @NotEmpty(message = "Sale must have at least one payment")
    @Valid
    private List<PaymentDto> payments;
}
