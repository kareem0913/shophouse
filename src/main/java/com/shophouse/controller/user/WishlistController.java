package com.shophouse.controller.user;

import com.shophouse.model.dto.wishlist.WishListResponse;
import com.shophouse.security.UserPrincipal;
import com.shophouse.service.customer.wishlist.WishListService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/wishlist")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Wishlist", description = "Wishlist management endpoints")
public class WishlistController {

    private final WishListService wishListService;

    @GetMapping
    public Page<WishListResponse> httpGetWishlist(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @AuthenticationPrincipal final UserPrincipal currentUser) {
        Pageable pageable = PageRequest.of(page, size);
        return wishListService.findAllWishlists(pageable, currentUser.getId());
    }

    @PostMapping("/{productId}")
    public WishListResponse httpAddProductToWishlist(@PathVariable final Long productId,
                                                     @AuthenticationPrincipal final UserPrincipal currentUser) {
        return wishListService.addProductToWishlist(productId, currentUser.getId());
    }

    @DeleteMapping("/{productId}")
    public void httpRemoveProductFromWishlist(@PathVariable final Long productId,
                                              @AuthenticationPrincipal final UserPrincipal currentUser) {
        wishListService.removeProductFromWishlist(productId, currentUser.getId());
    }
}
