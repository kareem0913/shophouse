package com.shophouse.repository;

import com.shophouse.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findById(Long id);

    @Override
    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findAll(Pageable pageable);

    @EntityGraph(value = "Product.detail", type = EntityGraph.EntityGraphType.LOAD)
    Page<Product> findByCategoriesId(Long categoryId, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") boolean status);

}
