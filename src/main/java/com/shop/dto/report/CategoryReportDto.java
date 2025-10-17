package com.shop.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReportDto {
    private String categoryName;
    private Long totalQuantitySold;
    private BigDecimal totalAmount;
}
