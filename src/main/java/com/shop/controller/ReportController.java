package com.shop.controller;

import com.shop.dto.report.CategoryReportDto;
import com.shop.dto.report.CustomerReportDto;
import com.shop.dto.report.DailySalesSummaryDto;
import com.shop.entity.MonthlySalesReport;
import com.shop.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reporting", description = "Endpoints for sales reports")
@PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly")
    public ResponseEntity<List<MonthlySalesReport>> getMonthlyReport(
            @RequestParam(defaultValue = "2025") int year) {
        return ResponseEntity.ok(reportService.getMonthlySalesReport(year));
    }

    @GetMapping("/daily")
    public ResponseEntity<List<DailySalesSummaryDto>> getDailyReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {
        return ResponseEntity.ok(reportService.getDailySalesReport(month));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryReportDto>> getCategoryReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getSalesByCategory(startDate, endDate));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerReportDto>> getCustomerReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getSalesByCustomer(startDate, endDate));
    }
}
