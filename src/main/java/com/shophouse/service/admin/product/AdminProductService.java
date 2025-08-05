package com.shophouse.service.admin.product;

import com.shophouse.model.dto.product.ProductCreate;
import com.shophouse.model.dto.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminProductService {
    ProductResponse findProduct(Long id);
    Page<ProductResponse> findAllProducts(Pageable pageable);
    ProductResponse createProduct(ProductCreate productCreate);
    ProductResponse updateProduct(Long id, ProductCreate productUpdate);
    Page<ProductResponse> findProductsByCategoryId(Long categoryId, Pageable pageable);
    void changeProductStatus(Long id, boolean status);
    void deleteProduct(Long id);
    Page<ProductResponse> searchProducts(String keyword, Pageable pageable);
}
