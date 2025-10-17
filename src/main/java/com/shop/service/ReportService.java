package com.shop.service;

import com.shop.dto.report.CategoryReportDto;
import com.shop.dto.report.CustomerReportDto;
import com.shop.dto.report.DailySalesSummaryDto;
import com.shop.entity.MonthlySalesReport;
import com.shop.repository.MonthlySalesReportRepository;
import com.shop.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SaleRepository saleRepository;
    private final MonthlySalesReportRepository monthlySalesReportRepository;

    public List<MonthlySalesReport> getMonthlySalesReport(int year) {
        return monthlySalesReportRepository.findByYear(year);
    }

    public List<DailySalesSummaryDto> getDailySalesReport(LocalDate month) {
        YearMonth yearMonth = YearMonth.from(month);
        var startDate = yearMonth.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        var endDate = yearMonth.atEndOfMonth().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return saleRepository.findDailySalesSummary(startDate, endDate);
    }

    public List<CategoryReportDto> getSalesByCategory(LocalDate start, LocalDate end) {
        var startDate = start.atStartOfDay().toInstant(ZoneOffset.UTC);
        var endDate = end.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return saleRepository.findCategorySalesReport(startDate, endDate);
    }

    public List<CustomerReportDto> getSalesByCustomer(LocalDate start, LocalDate end) {
        var startDate = start.atStartOfDay().toInstant(ZoneOffset.UTC);
        var endDate = end.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return saleRepository.findCustomerSalesReport(startDate, endDate);
    }
}
