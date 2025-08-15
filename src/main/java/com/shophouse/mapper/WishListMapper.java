package com.shophouse.mapper;

import com.shophouse.config.AppProperties;
import com.shophouse.model.dto.wishlist.WishListResponse;
import com.shophouse.model.entity.ProductImage;
import com.shophouse.model.entity.WishList;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class WishListMapper {

    public static WishListResponse toWishListResponse(WishList wishList, AppProperties appProperties) {

        Set<String> images = wishList.getProduct().getImagesUrls()
                .stream()
                .map(image -> appProperties.getAppUrl() + image.getImageUrl())
                .collect(Collectors.toSet());

        return WishListResponse.builder()
                .id(wishList.getId())
                .productId(wishList.getProduct().getId())
                .productName(wishList.getProduct().getName())
                .imagesUrls(images)
                .build();
    }
}
