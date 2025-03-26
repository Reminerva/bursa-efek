package com.bursaefek.bursaefek.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bursaefek.bursaefek.constant.DbBash;
import com.bursaefek.bursaefek.entity.CategoryProduct;
import com.bursaefek.bursaefek.model.request.NewCategoryProductRequest;
import com.bursaefek.bursaefek.model.response.CategoryProductResponse;
import com.bursaefek.bursaefek.repository.CategoryProductRepository;
import com.bursaefek.bursaefek.service.CategoryProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryProductServiceImpl implements CategoryProductService {
    private final CategoryProductRepository categoryProductRepository;

    @Override
    public CategoryProductResponse create(NewCategoryProductRequest newCategoryProductRequest) {
        CategoryProduct categoryProduct = CategoryProduct.builder()
                                            .name(newCategoryProductRequest.getName())
                                            .build();

        categoryProductRepository.saveAndFlush(categoryProduct);

        CategoryProductResponse categoryProductResponse = CategoryProductResponse.builder()
            .id(categoryProduct.getId())
            .name(categoryProduct.getName())
            .products(null)
            .build();
        return categoryProductResponse; 
    }

    @Override
    public CategoryProduct getCategoryProductById(String id) {
        Optional<CategoryProduct> categoryProduct = categoryProductRepository.findById(id);
        if (categoryProduct.isEmpty()) throw new RuntimeException(DbBash.CATEGORY_PRODUCT_NOT_FOUND);
        return categoryProduct.get();
    }

    @Override
    public CategoryProductResponse getById(String id) {
        try {
            CategoryProduct categoryProduct = getCategoryProductById(id);
            CategoryProductResponse categoryProductResponse = CategoryProductResponse.builder()
                .id(categoryProduct.getId())
                .name(categoryProduct.getName())
                .products(categoryProduct.getProducts().stream().map(product -> product.getId()).toList())
                .build();
            return categoryProductResponse;
        } catch (Exception e) {
            throw new RuntimeException(DbBash.CATEGORY_PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public List<CategoryProductResponse> getAll() {
        List<CategoryProduct> categoryProducts = categoryProductRepository.findAll();
        List<CategoryProductResponse> categoryProductResponses = categoryProducts.stream().map(categoryProduct -> CategoryProductResponse.builder()
            .id(categoryProduct.getId())
            .name(categoryProduct.getName())            
            .products(categoryProduct.getProducts().stream().map(product -> product.getId()).toList())
            .build()).toList();
        return categoryProductResponses;
    }

    @Override
    public CategoryProductResponse update(NewCategoryProductRequest newCategoryProductRequest, String id) {
        try {
            
            CategoryProduct categoryProductLatest = getCategoryProductById(id);
            CategoryProduct categoryProductNewest = CategoryProduct.builder()
                                                .id(id)
                                                .name(newCategoryProductRequest.getName())
                                                .build();
            CategoryProductResponse categoryProductResponse = CategoryProductResponse.builder()
                                                .id(id)
                                                .name(categoryProductNewest.getName())
                                                .products(categoryProductLatest.getProducts().stream().map(product -> product.getId()).toList())
                                                .build();

            categoryProductRepository.saveAndFlush(categoryProductNewest);
            
            return categoryProductResponse;
        } catch (Exception e) {
            throw new RuntimeException(DbBash.CATEGORY_PRODUCT_NOT_FOUND);
        }
    }


}
