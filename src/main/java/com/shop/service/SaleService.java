package com.shop.service;

import com.shop.dto.PaymentDto;
import com.shop.dto.SaleItemDto;
import com.shop.dto.SaleRequestDto;
import com.shop.entity.*;
import com.shop.repository.CustomerRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AtomicLong invoiceCounter = new AtomicLong(0);


    @Transactional
    public Sale createSale(SaleRequestDto saleRequestDto) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Sale sale = new Sale();
        sale.setSaleDate(Instant.now());
        sale.setUser(currentUser);
        sale.setInvoiceNumber(generateInvoiceNumber());

        if (saleRequestDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(saleRequestDto.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            sale.setCustomer(customer);
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleItem> saleItems = new ArrayList<>();

        for (SaleItemDto itemDto : saleRequestDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + itemDto.getProductId()));

            if (product.getQuantity() < itemDto.getQuantity()) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setQuantity(itemDto.getQuantity());
            saleItem.setUnitPrice(product.getSellingPrice());
            saleItem.setTotalPrice(product.getSellingPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
            saleItems.add(saleItem);

            totalAmount = totalAmount.add(saleItem.getTotalPrice());
        }

        sale.setSaleItems(saleItems);
        sale.setTotalAmount(totalAmount);
        sale.setDiscount(saleRequestDto.getDiscount());
        BigDecimal netAmount = totalAmount.subtract(saleRequestDto.getDiscount());
        sale.setNetAmount(netAmount);

        BigDecimal totalPaid = saleRequestDto.getPayments().stream()
                .map(PaymentDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPaid.compareTo(netAmount) < 0) {
            throw new IllegalArgumentException("Payment is less than the net amount.");
        }

        List<Payment> payments = new ArrayList<>();
        for (PaymentDto paymentDto : saleRequestDto.getPayments()) {
            Payment payment = new Payment();
            payment.setSale(sale);
            payment.setAmount(paymentDto.getAmount());
            payment.setMethod(paymentDto.getMethod());
            payment.setPaymentDate(Instant.now());
            payments.add(payment);
        }
        sale.setPayments(payments);

        return saleRepository.save(sale);
    }

    @Transactional(readOnly = true)
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found with id: " + id));
    }

    private String generateInvoiceNumber() {
        // e.g., INV-20251017-0001
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = saleRepository.count();
        return String.format("INV-%s-%04d", datePart, count + 1);
    }
}

