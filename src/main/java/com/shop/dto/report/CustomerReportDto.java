package com.shop.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportDto {
    private Long customerId;
    private String customerName;
    private Long totalSales;
    private BigDecimal totalAmount;
}
