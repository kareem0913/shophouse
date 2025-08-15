package com.shophouse.service.customer.wishlist;

import com.shophouse.model.dto.wishlist.WishListResponse;
import com.shophouse.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishListService {

    Page<WishListResponse> findAllWishlists(Pageable pageable, Long userId);

    WishListResponse addProductToWishlist(Long productId, Long userId);

    void removeProductFromWishlist(Long productId, Long userId);
}
