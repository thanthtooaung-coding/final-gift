package com.shop.service;

import com.shop.dto.ProductDto;
import com.shop.entity.Product;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return convertToDto(product);
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        existingProduct.setName(productDto.getName());
        existingProduct.setSku(productDto.getSku());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setCostPrice(productDto.getCostPrice());
        existingProduct.setSellingPrice(productDto.getSellingPrice());
        existingProduct.setQuantity(productDto.getQuantity());

        if (productDto.getCategoryId() != null) {
            var category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDto.getCategoryId()));
            existingProduct.setCategory(category);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = modelMapper.map(product, ProductDto.class);
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        if (productDto.getCategoryId() != null) {
            var category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDto.getCategoryId()));
            product.setCategory(category);
        }
        return product;
    }
}

