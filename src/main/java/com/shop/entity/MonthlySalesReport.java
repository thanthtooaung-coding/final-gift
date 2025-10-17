package com.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Data
@Entity
@Immutable
@Table(name = "monthly_sales_report")
public class MonthlySalesReport {

    @Id
    private String month;

    private Integer year;

    @Column(name = "month_of_year")
    private Integer monthOfYear;

    @Column(name = "total_sales_count")
    private Long totalSalesCount;

    @Column(name = "total_items_sold")
    private Long totalItemsSold;

    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "net_revenue")
    private BigDecimal netRevenue;
}
