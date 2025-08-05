package com.shophouse.service.admin.product;

import com.shophouse.mapper.ProductMapper;
import com.shophouse.model.dto.product.ProductCreate;
import com.shophouse.model.dto.product.ProductResponse;
import com.shophouse.model.entity.Category;
import com.shophouse.model.entity.Product;
import com.shophouse.model.entity.ProductImage;
import com.shophouse.repository.CategoryRepository;
import com.shophouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        return null;
    }

    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        return null;
    }

    @Override
    public ProductResponse createProduct(final ProductCreate productCreate) {
        Product product = productMapper.toProduct(productCreate);
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(productCreate.getCategoriesIds()));
        product.setCategories(categories);

        Set<ProductImage> images = productCreate.getImages()
                .stream()
                .map(image -> {
                    String stored = saveFile(image, uploadDir, "products");
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(stored);
                    productImage.setProduct(product);
                    return productImage;
                })
                .collect(Collectors.toSet());
        product.setImagesUrls(images);

        Product savedProduct = productRepository.save(product);
        log.info("Product with ID {} created successfully", savedProduct.getId());

        return null;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductCreate productUpdate) {
        return null;
    }

    @Override
    public Page<ProductResponse> findProductsByCategoryId(Long categoryId, Pageable pageable) {
        return null;
    }

    @Override
    public void changeProductStatus(Long id, boolean status) {

    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        return null;
    }
}
