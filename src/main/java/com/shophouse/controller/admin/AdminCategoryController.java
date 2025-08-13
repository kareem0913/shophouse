package com.shophouse.controller.admin;

import com.shophouse.model.dto.category.CategoryCreate;
import com.shophouse.model.dto.category.CategoryResponse;
import com.shophouse.service.admin.category.AdminCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public Page<CategoryResponse> httpGetAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return adminCategoryService.findAllCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryResponse httpGetCategoryById(@NotNull @PathVariable Long id) {
        return adminCategoryService.findCategory(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public CategoryResponse httpCreateCategory(@Valid @ModelAttribute @NotNull CategoryCreate categoryCreate) {
        return adminCategoryService.createCategory(categoryCreate);
    }

    @PutMapping(consumes = {"multipart/form-data"}, value = "/{id}")
    public CategoryResponse httpUpdateCategory(
            @PathVariable Long id,
            @Valid @ModelAttribute @NotNull CategoryCreate categoryCreate
    ) {
        return adminCategoryService.updateCategory(id, categoryCreate);
    }

    @GetMapping("/parent/{parentId}")
    public Page<CategoryResponse> httpGetCategoriesByParentId(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return adminCategoryService.findCategoriesByParentId(parentId, pageable);
    }

    @PutMapping("/{id}/status")
    public void httpChangeCategoryStatus(@PathVariable Long id, @RequestParam boolean status) {
        adminCategoryService.changeCategoryStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void httpDeleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
    }
}
