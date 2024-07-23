package com.tigmaminds.ecommerce.service;

import com.tigmaminds.ecommerce.dto.ProductDto;

import java.util.List;

public interface ProductService {
    public int createProduct(ProductDto productDto);
    public int deleteProduct(Integer productId);
    public int editProduct(ProductDto productDto);
    public List<ProductDto> getAllProduct();
    public ProductDto getProductById(Integer productId);
}
