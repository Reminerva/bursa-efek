package com.bursaefek.bursaefek.entity;

import java.util.List;

import com.bursaefek.bursaefek.constant.DbBash;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = DbBash.CATEGORY_PRODUCT_DB)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "categoryProduct")
    private List<Product> products;
}
