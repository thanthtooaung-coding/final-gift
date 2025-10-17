package com.shop.repository;

import com.shop.entity.MonthlySalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonthlySalesReportRepository extends JpaRepository<MonthlySalesReport, String> {

    @Query(value = "SELECT * FROM monthly_sales_report WHERE year = :year", nativeQuery = true)
    List<MonthlySalesReport> findByYear(@Param("year") int year);
}
