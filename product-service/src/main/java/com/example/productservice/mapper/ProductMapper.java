package com.example.productservice.mapper;

import com.example.productservice.domain.Product;
import com.example.productservice.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);

    ProductDTO toProductDTO(Product product);
}
