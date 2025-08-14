package com.shophouse.service.customer.product;


import com.shophouse.error.exception.ResourceNotFoundException;
import com.shophouse.mapper.ProductMapper;
import com.shophouse.model.dto.product.ProductResponse;
import com.shophouse.model.entity.Product;
import com.shophouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findByIdAndStatusTrue(id).orElseThrow(() -> {
            log.error("Product with ID {} not found", id);
            return new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        });
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAllByStatusTrue(pageable);
        return products.map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> findProductsByCategoryId(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findAllByCategoriesIdAndStatusTrue(categoryId, pageable);
        return products.map(productMapper::toProductResponse);
    }
}
