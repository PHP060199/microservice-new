package com.example.productservice.controller;

import com.example.common.dto.respone.ApiResponse;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductDTO> creteProduct(@RequestBody ProductDTO productRequest) {
        return ApiResponse.<ProductDTO>builder()
                .result(productService.createProduct(productRequest))
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDTO>> getAllProduct() {
       return ApiResponse.<List<ProductDTO>>builder()
               .result(productService.getAllProducts())
               .build();
    }

    @GetMapping(value = "/getByCondition")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDTO>> getProductsByCondition(@RequestParam (name = "name") String name,
                                                   @RequestParam (name = "price") Float price) {
        return ApiResponse.<List<ProductDTO>>builder()
                .result(productService.findProductByCondition(name, price))
                .build();
    }

    @GetMapping(value = "/getById")
    public ApiResponse<ProductDTO> getProductById(@RequestParam Long id) {
        return ApiResponse.<ProductDTO>builder()
                .result(productService.getProductById(id))
                .build();
    }

    @PutMapping()
    public ApiResponse<ProductDTO> updateProductById(@RequestBody ProductDTO productDTO) {
        return ApiResponse.<ProductDTO>builder()
                .result(productService.updateProductById(productDTO))
                .build();
    }

    @DeleteMapping()
    public ApiResponse<String> deleteProductById(@RequestParam Long id) {
        productService.deleteProductById(id);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }
}
