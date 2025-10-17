package com.shop.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface DailySalesSummaryDto {
    Date getSaleDate();
    Long getTotalSales();
    BigDecimal getTotalAmount();
}
