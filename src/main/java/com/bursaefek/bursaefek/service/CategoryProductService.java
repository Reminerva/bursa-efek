package com.bursaefek.bursaefek.service;

import java.util.List;

import com.bursaefek.bursaefek.entity.CategoryProduct;
import com.bursaefek.bursaefek.model.request.NewCategoryProductRequest;
import com.bursaefek.bursaefek.model.response.CategoryProductResponse;

public interface CategoryProductService {
    CategoryProductResponse create(NewCategoryProductRequest newCategoryProductRequest);
    CategoryProductResponse getById(String id);
    CategoryProduct getCategoryProductById(String id);
    List<CategoryProductResponse> getAll();
    CategoryProductResponse update(NewCategoryProductRequest newCategoryProductRequest, String id);
}
