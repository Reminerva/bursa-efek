package com.bursaefek.bursaefek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bursaefek.bursaefek.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
