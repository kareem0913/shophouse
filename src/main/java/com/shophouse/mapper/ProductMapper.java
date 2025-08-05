package com.shophouse.mapper;

import com.shophouse.config.AppProperties;
import com.shophouse.model.dto.product.ProductCreate;
import com.shophouse.model.dto.product.ProductResponse;
import com.shophouse.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected AppProperties appProperties;

    public abstract ProductResponse toProductResponse(Product product);

    @Mapping(target = "imagesUrls", ignore = true)
    @Mapping(target = "categories", ignore = true)
    public abstract Product toProduct(ProductCreate productCreate);

    public abstract Product toProduct(ProductResponse productResponse);

}
