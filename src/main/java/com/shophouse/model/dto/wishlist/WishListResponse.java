package com.shophouse.model.dto.wishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishListResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Set<String> imagesUrls;
}
