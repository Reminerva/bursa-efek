package com.bursaefek.bursaefek.service;

import java.util.List;

import com.bursaefek.bursaefek.model.request.NewProductRequest;
import com.bursaefek.bursaefek.model.response.ProductResponse;

public interface ProductService {
    ProductResponse create(NewProductRequest newProductRequest);
    List<ProductResponse> getAll();
    ProductResponse getById(String id);
    ProductResponse update(NewProductRequest newProductRequest, String id);
    void hardDelete(String id);
    void softDelete(String id);
}
