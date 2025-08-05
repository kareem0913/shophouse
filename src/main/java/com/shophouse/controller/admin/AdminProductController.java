package com.shophouse.controller.admin;

import com.shophouse.model.dto.product.ProductCreate;
import com.shophouse.model.dto.product.ProductResponse;
import com.shophouse.service.admin.product.AdminProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping
    public Page<ProductResponse> httpGetAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return adminProductService.findAllProducts(pageable);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse httpCreateProduct(@Valid @NotNull @ModelAttribute ProductCreate productCreate) {
        return adminProductService.createProduct(productCreate);
    }
}
