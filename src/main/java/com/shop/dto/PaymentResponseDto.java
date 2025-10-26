package com.shop.dto;

import com.shop.entity.PaymentMethod;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentResponseDto {
    private Long id;
    private Instant paymentDate;
    private BigDecimal amount;
    private PaymentMethod method;
}
