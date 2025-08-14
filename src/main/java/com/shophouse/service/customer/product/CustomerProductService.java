package com.shophouse.service.customer.product;

import com.shophouse.model.dto.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerProductService {

    ProductResponse findProduct(Long id);

    Page<ProductResponse> findAllProducts(Pageable pageable);

    Page<ProductResponse> findProductsByCategoryId(Long categoryId, Pageable pageable);
}
