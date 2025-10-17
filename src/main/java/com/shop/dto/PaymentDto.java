package com.shop.dto;

import com.shop.entity.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentDto {
    @NotNull(message = "Payment amount is required")
    @Min(value = 0, message = "Payment amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod method;
}
