package com.shophouse.service.customer.category;

import com.shophouse.error.exception.ResourceNotFoundException;
import com.shophouse.mapper.CategoryMapper;
import com.shophouse.model.dto.category.CategoryResponse;
import com.shophouse.model.entity.Category;
import com.shophouse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerCategoryServiceImpl implements CustomerCategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse findCategory(Long id) {
        Category category = categoryRepository.findByIdAndStatusTrue(id).orElseThrow(() -> {
            log.error("Category with ID {} not found", id);
            return new ResourceNotFoundException("Category not found", "No category found with the provided ID: " + id);
        });
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> findAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAllByStatusTrue(pageable);
        return categoryPage.map(categoryMapper::toCategoryResponse);
    }

    @Override
    public Page<CategoryResponse> findCategoriesByParentId(Long parentId, Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAllByParentIdAndStatusTrue(parentId, pageable);
        return categoryPage.map(categoryMapper::toCategoryResponse);
    }

}
