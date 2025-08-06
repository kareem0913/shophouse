package com.shophouse.mapper;

import com.shophouse.config.AppProperties;
import com.shophouse.model.dto.product.ProductCreate;
import com.shophouse.model.dto.product.ProductResponse;
import com.shophouse.model.entity.Category;
import com.shophouse.model.entity.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public abstract class ProductMapper {

    @Autowired
    protected AppProperties appProperties;

    @Mapping(target = "imagesUrls", ignore = true)
    public abstract ProductResponse toProductResponse(Product product);

    @Mapping(target = "imagesUrls", ignore = true)
    @Mapping(target = "categories", ignore = true)
    public abstract Product toProduct(ProductCreate productCreate);

//    public abstract Product  toProduct(ProductResponse productResponse);


    @AfterMapping
    protected void setImageUrls(Product product, @MappingTarget ProductResponse productResponse) {
        if (product.getImagesUrls() != null) {
            productResponse.setImagesUrls(product.getImagesUrls().stream()
                    .map(image -> appProperties.getAppUrl() + image.getImageUrl())
                    .collect(Collectors.toSet()));
        }
    }
}
