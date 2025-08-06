package com.shophouse.service.admin.product;

import com.shophouse.error.exception.ResourceNotFoundException;
import com.shophouse.mapper.ProductMapper;
import com.shophouse.model.dto.product.ProductCreate;
import com.shophouse.model.dto.product.ProductResponse;
import com.shophouse.model.entity.Category;
import com.shophouse.model.entity.Product;
import com.shophouse.model.entity.ProductImage;
import com.shophouse.repository.CategoryRepository;
import com.shophouse.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.shophouse.util.Util.deleteFile;
import static com.shophouse.util.Util.saveFile;

@Service
@Slf4j
public class AdminProductServiceImpl implements AdminProductService{

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Path uploadDir;

    public AdminProductServiceImpl(ProductRepository productRepository,
                                   ProductMapper productMapper,
                                   CategoryRepository categoryRepository,
                                   @Value("${file.upload-dir:uploads}") String dir
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.uploadDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    @Override
    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product with ID {} not found", id);
            return new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        });
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toProductResponse);
    }

    @Override
    public ProductResponse createProduct(final ProductCreate productCreate) {
        Product product = productMapper.toProduct(productCreate);
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(productCreate.getCategoriesIds()));
        product.setCategories(categories);

        List<ProductImage> images = productCreate.getImages()
                .stream()
                .map(image -> {
                    String stored = saveFile(image, uploadDir, "products");
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(stored);
                    productImage.setProduct(product);
                    return productImage;
                })
                .toList();
        log.info("Built images set: size={}", images.size());
        product.setImagesUrls(images);

        Product savedProduct = productRepository.save(product);
        log.info("Product with ID {} created successfully", savedProduct.getId());
        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductCreate productUpdate) {
        return null;
    }

    @Override
    public Page<ProductResponse> findProductsByCategoryId(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoriesId(categoryId, pageable);
        return products.map(productMapper::toProductResponse);
    }

    @Transactional
    @Override
    public void changeProductStatus(Long id, boolean status) {
        if(!productRepository.existsById(id) ) {
            log.error("Product with ID {} not found", id);
            throw new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        }
        productRepository.updateStatusById(id, status);
        log.info("Product with ID {} status changed to {}", id, status ? "active" : "inactive");
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product with ID {} not found", id);
            return new ResourceNotFoundException("Product not found",
                    "No product found with the provided ID: " + id);
        });

        if (product.getImagesUrls() != null) {
            product.getImagesUrls().forEach(image -> {
                if (image.getImageUrl() != null) {
                    deleteFile(uploadDir, image.getImageUrl());
                }
            });
        }

        productRepository.delete(product);
        log.info("Product with ID {} deleted successfully", id);
    }

    @Override
    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        return null;
    }
}
