package com.tigmaminds.ecommerce.service.impl;

import com.tigmaminds.ecommerce.dto.ProductDto;
import com.tigmaminds.ecommerce.entity.ProductEntity;
import com.tigmaminds.ecommerce.repository.ProductRepository;
import com.tigmaminds.ecommerce.service.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductEntity productDtoToEntityMapper(@NotNull ProductDto productDto){
        return ProductEntity.builder()
                .name(productDto.getName())
                .brand(productDto.getBrand())
                .price(productDto.getPrice())
                .category(productDto.getCategory()).build();
    }

    ProductDto productEntityToDtoMapper(@NotNull ProductEntity productEntity){
        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .brand(productEntity.getBrand())
                .price(productEntity.getPrice())
                .category(productEntity.getCategory()).build();
    }

    void productUpdate(ProductEntity productEntity, ProductDto productDto){
        if(productDto.getName()!=null)
            productEntity.setName(productDto.getName());
        if(productDto.getBrand()!=null)
            productEntity.setBrand(productDto.getBrand());
        if(productDto.getCategory()!=null)
            productEntity.setCategory(productDto.getCategory());
        if(productDto.getPrice()>0)
            productEntity.setPrice(productDto.getPrice());
    }

    @Override
    public int createProduct(ProductDto productDto) {
        int count = productRepository.countBySameNameAndBrand(productDto.getName(),productDto.getBrand());
        if(count>0)
            return 409;
        productRepository.save(productDtoToEntityMapper(productDto));
        return 200;
    }

    @Override
    public int deleteProduct(Integer productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        if(optionalProductEntity.isPresent()){
            productRepository.deleteById(productId);
            return 200;
        }
        return 404;
    }

    @Override
    public int editProduct(ProductDto productDto) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productDto.getId());
        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();
            productUpdate(productEntity,productDto);
            productRepository.save(productEntity);
            return 200;
        }
        return 404;
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<ProductDto> productDtoList = new ArrayList<>();
        productRepository.findAll().forEach((productEntity -> {
            productDtoList.add(productEntityToDtoMapper(productEntity));
        }));
        return productDtoList;
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        ProductDto productDto;
        productDto = productEntityToDtoMapper(productRepository.findById(productId).orElseThrow());
        return productDto;
    }
}
