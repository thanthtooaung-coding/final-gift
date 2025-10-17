package com.shop.repository;

import com.shop.dto.report.CategoryReportDto;
import com.shop.dto.report.CustomerReportDto;
import com.shop.dto.report.DailySalesSummaryDto;
import com.shop.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = """
        SELECT
            CAST(s.sale_date AS DATE) AS saleDate,
            COUNT(s.id) AS totalSales,
            SUM(s.net_amount) AS totalAmount
        FROM sales s
        WHERE s.sale_date >= :startDate AND s.sale_date < :endDate
        GROUP BY CAST(s.sale_date AS DATE)
        ORDER BY saleDate ASC
    """, nativeQuery = true)
    List<DailySalesSummaryDto> findDailySalesSummary(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query("""
        SELECT new com.shop.dto.report.CategoryReportDto(
            p.category.name,
            SUM(si.quantity),
            SUM(si.totalPrice)
        )
        FROM SaleItem si
        JOIN si.product p
        WHERE si.sale.saleDate BETWEEN :startDate AND :endDate
        GROUP BY p.category.name
        ORDER BY p.category.name
    """)
    List<CategoryReportDto> findCategorySalesReport(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
    
    @Query("""
        SELECT new com.shop.dto.report.CustomerReportDto(
            s.customer.id,
            s.customer.name,
            COUNT(s.id),
            SUM(s.netAmount)
        )
        FROM Sale s
        WHERE s.customer IS NOT NULL AND s.saleDate BETWEEN :startDate AND :endDate
        GROUP BY s.customer.id, s.customer.name
        ORDER BY SUM(s.netAmount) DESC
    """)
    List<CustomerReportDto> findCustomerSalesReport(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
