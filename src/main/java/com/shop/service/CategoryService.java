package com.shop.service;

import com.shop.dto.CategoryDto;
import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCreatedAt(Instant.now());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Transactional
    public void deleteCategory(Long id, boolean hardDelete) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }

        boolean hasAssociatedProducts = productRepository.existsByCategoryId(id);

        if (hasAssociatedProducts) {
            if (!hardDelete) {
                throw new IllegalStateException("Cannot delete category because it has associated products. Use hardDelete=true to force.");
            } else {
                List<Product> productsToUpdate = productRepository.findByCategoryId(id);
                for (Product product : productsToUpdate) {
                    product.setCategory(null);
                }
                productRepository.saveAll(productsToUpdate);
            }
        }
        
        categoryRepository.deleteById(id);
    }
}
