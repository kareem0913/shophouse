package com.shophouse.service.customer.category;

import com.shophouse.model.dto.category.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerCategoryService{

    CategoryResponse findCategory(Long id);

    Page<CategoryResponse> findAllCategories(Pageable pageable);

    Page<CategoryResponse> findCategoriesByParentId(Long parentId, Pageable pageable);
}
