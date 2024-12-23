package com.example.productservice.service;



import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductDTO;
import com.example.productservice.exception.CustomException;
import com.example.productservice.exception.define.ErrorCode;
import com.example.productservice.exception.define.ErrorMessage;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductDTO createProduct(ProductDTO productDTO) {

        if (productRepository.existsByName(productDTO.getName())) {
            throw new CustomException(ErrorMessage.PRODUCT_ALREADY_EXISTS, ErrorCode.exist);
        }

        Product product = productMapper.toProduct(productDTO);
        return productMapper.toProductDTO(productRepository.save(product));
    }


    public ProductDTO updateProductById(ProductDTO productDTO) {
        productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new CustomException(ErrorMessage.PRODUCT_NOT_FOUND, ErrorCode.notFound));

        if (productRepository.existsByName(productDTO.getName())) {
            throw new CustomException(ErrorMessage.PRODUCT_ALREADY_EXISTS, ErrorCode.exist);
        }
        Product updateProduct = productMapper.toProduct(productDTO);
        return productMapper.toProductDTO(productRepository.save(updateProduct));
    }


    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductDTO).toList();
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.PRODUCT_NOT_FOUND, ErrorCode.notFound));
        return productMapper.toProductDTO(product);
    }

    public List<ProductDTO> findProductByCondition(String name, Float price) {
        List<Product> productList = productRepository.getProductsByNameOrPrice(name, price);
        return productList.stream().map(productMapper::toProductDTO).toList();
    }

    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.PRODUCT_NOT_FOUND, ErrorCode.notFound));
        productRepository.deleteById(id);
    }
}
