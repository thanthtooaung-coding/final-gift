package com.shop.dto.report;

import java.math.BigDecimal;

public interface MonthlySalesReportDto {
    String getMonth();
    Integer getYear();
    Long getTotalSalesCount();
    Long getTotalItemsSold();
    BigDecimal getTotalDiscount();
    BigDecimal getTotalRevenue();
    BigDecimal getNetRevenue();
}
