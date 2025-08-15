package com.shophouse.repository;

import com.shophouse.model.entity.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    Page<WishList> findAllByUserId(Long userId, Pageable pageable);

    @Modifying
    void deleteByProductIdAndUserId(Long productId, Long userId);

}
