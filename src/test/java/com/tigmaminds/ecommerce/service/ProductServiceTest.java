package com.tigmaminds.ecommerce.service;

import com.tigmaminds.ecommerce.dto.ProductDto;
import com.tigmaminds.ecommerce.dto.UserDetailDTO;
import com.tigmaminds.ecommerce.entity.ProductEntity;
import com.tigmaminds.ecommerce.repository.ProductRepository;
import com.tigmaminds.ecommerce.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ProductServiceTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    ProductDto productDto = new ProductDto("Test Product", "Test Brand", "Test Category", 100);
    UserDetailDTO userDetailDTO = new UserDetailDTO("phijose@gmail.com","1234qwer","user");
    ProductEntity productEntity = new ProductEntity();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_Success() {
        when(productRepository.countBySameNameAndBrand(any(String.class), any(String.class))).thenReturn(0);
        int result = productService.createProduct(productDto);
        assertEquals(200, result);
    }

    @Test
    public void testCreateProduct_Conflict() {
        when(productRepository.countBySameNameAndBrand(productDto.getName(), productDto.getBrand())).thenReturn(1);
        int result = productService.createProduct(productDto);
        assertEquals(409, result);
    }

    @Test
    public void testDeleteProduct_ProductFound() {
        Integer productId = 1;
        productEntity.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        doNothing().when(productRepository).deleteById(productId);
        int result = productService.deleteProduct(productId);
        assertEquals(200, result);
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        int result = productService.deleteProduct(productId);
        assertEquals(404, result);
    }

    @Test
    void testEditProduct_ProductExists() {
        when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(productEntity));
        int result = productService.editProduct(productDto);
        assertEquals(200, result);
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void testEditProduct_ProductDoesNotExist() {
        when(productRepository.findById(productDto.getId())).thenReturn(Optional.empty());
        int result = productService.editProduct(productDto);
        assertEquals(404, result);
        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    @Test
    void testGetAllProduct() {
        List<ProductEntity> productEntities = new ArrayList<>();
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(1);
        productEntities.add(productEntity1);
        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(2);
        productEntities.add(productEntity2);
        when(productRepository.findAll()).thenReturn(productEntities);
        List<ProductDto> result = productService.getAllProduct();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(productEntity1.getId(), result.get(0).getId());
        assertEquals(productEntity2.getId(), result.get(1).getId());
    }

    @Test
    void testGetProductById_ProductExists() {
        Integer productId = 1;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        ProductDto result = productService.getProductById(productId);
        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductById_ProductDoesNotExist() {
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }


}
