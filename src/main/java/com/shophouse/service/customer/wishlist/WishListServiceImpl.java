package com.shophouse.service.customer.wishlist;

import com.shophouse.config.AppProperties;
import com.shophouse.error.exception.ResourceNotFoundException;
import com.shophouse.mapper.WishListMapper;
import com.shophouse.model.dto.wishlist.WishListResponse;
import com.shophouse.model.entity.Product;
import com.shophouse.model.entity.User;
import com.shophouse.model.entity.WishList;
import com.shophouse.repository.ProductRepository;
import com.shophouse.repository.UserRepository;
import com.shophouse.repository.WishListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AppProperties appProperties;

    @Override
    public Page<WishListResponse> findAllWishlists(Pageable pageable, Long userId) {
        return wishListRepository.findAllByUserId(userId, pageable)
                .map(wishList -> WishListMapper.toWishListResponse(wishList, appProperties));
    }

    @Override
    public WishListResponse addProductToWishlist(Long productId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            return new ResourceNotFoundException("User not found", "user not found with id: " + userId);
        });

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            return new ResourceNotFoundException("Product not found", "product not found with id: " + productId);
        });

        WishList wishList = new WishList();
        wishList.setUser(user);
        wishList.setProduct(product);

        wishListRepository.save(wishList);

        return WishListMapper.toWishListResponse(wishList, appProperties);
    }

    @Override
    @Transactional
    public void removeProductFromWishlist(Long productId, Long userId) {
        wishListRepository.deleteByProductIdAndUserId(productId, userId);
    }
}
