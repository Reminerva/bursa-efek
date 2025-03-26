package com.bursaefek.bursaefek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bursaefek.bursaefek.entity.CategoryProduct;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, String> {
}
