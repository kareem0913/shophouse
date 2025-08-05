package com.shophouse.model.entity;

import com.shophouse.base.BaseEntity;
import com.shophouse.model.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity<Long> {

    private String name;
    private String description;
    private double price;
    private double discount;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Integer quantity;
    private boolean status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductImage> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name= "product_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories;

}
