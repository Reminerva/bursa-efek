package com.bursaefek.bursaefek.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bursaefek.bursaefek.constant.DbBash;
import com.bursaefek.bursaefek.entity.CategoryProduct;
import com.bursaefek.bursaefek.entity.Product;
import com.bursaefek.bursaefek.model.request.NewProductRequest;
import com.bursaefek.bursaefek.model.response.ProductResponse;
import com.bursaefek.bursaefek.repository.ProductRepository;
import com.bursaefek.bursaefek.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryProductServiceImpl categoryProductService;

    @Override
    public ProductResponse create(NewProductRequest newProductRequest) {
        try {
            CategoryProduct categoryProduct = categoryProductService.getCategoryProductById(newProductRequest.getCategoryProductId());
            Product product = Product.builder()
                .productName(newProductRequest.getProductName())
                .price(newProductRequest.getPrice())
                .stock(newProductRequest.getStock())
                .isPriceActive(true)
                .priceDate(LocalDate.now())
                .categoryProduct(categoryProduct)
                .build();
            productRepository.saveAndFlush(product);

            ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryProductId(product.getCategoryProduct().getId())
                .build();
            return productResponse;
        } catch (Exception e) {
            throw new RuntimeException(DbBash.CATEGORY_PRODUCT_NOT_FOUND);
        }
        
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map(product -> ProductResponse.builder()
            .id(product.getId())
            .productName(product.getProductName())
            .price(product.getPrice())
            .stock(product.getStock())
            .categoryProductId(product.getCategoryProduct().getId())
            .build()).toList();
        
        return productResponses;
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException(DbBash.PRODUCT_NOT_FOUND));
        ProductResponse productResponse = ProductResponse.builder()
            .id(product.getId())
            .productName(product.getProductName())
            .price(product.getPrice())
            .stock(product.getStock())
            .categoryProductId(product.getCategoryProduct().getId())
            .build();
        return productResponse;
    }

    @Override
    public ProductResponse update(NewProductRequest newProductRequest, String id) {
        try {
            Product productLatest = productRepository.findById(id).orElseThrow(() -> new RuntimeException(DbBash.PRODUCT_NOT_FOUND));
            Product product = Product.builder()
                .id(id)
                .productName(newProductRequest.getProductName())
                .price(newProductRequest.getPrice())
                .stock(newProductRequest.getStock())
                .isPriceActive(true)
                .priceDate(productLatest.getPriceDate())
                .build();
            
            productRepository.saveAndFlush(product);

            ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryProductId(product.getCategoryProduct().getId())
                .build();
            return productResponse;
        } catch (Exception e) {
            throw new RuntimeException(DbBash.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void hardDelete(String id) {
        try {
            productRepository.findById(id).orElseThrow(() -> new RuntimeException(DbBash.PRODUCT_NOT_FOUND));
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(DbBash.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void softDelete(String id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException(DbBash.PRODUCT_NOT_FOUND));
            product.setIsPriceActive(false);
            productRepository.saveAndFlush(product);
        } catch (Exception e) {
            throw new RuntimeException(DbBash.PRODUCT_NOT_FOUND);
        }
    }

}
