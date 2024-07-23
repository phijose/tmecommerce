package com.tigmaminds.ecommerce.controller;

import com.tigmaminds.ecommerce.dto.ProductDto;
import com.tigmaminds.ecommerce.dto.ResponseDTO;
import com.tigmaminds.ecommerce.service.AuthService;
import com.tigmaminds.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductService productService;
    AuthService authService;
    int response;

    public ProductController(ProductService productService,AuthService authService){
        this.productService = productService;
        this.authService = authService;
    }

    private <T> ResponseEntity<ResponseDTO<T>> responsBuilder(T message,HttpServletRequest request,HttpStatus status){
        return new ResponseEntity<>(new ResponseDTO<>(message,request.getRequestURI()),status);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<String>> createProduct(
            @RequestHeader(value = "User-Agent") String username
            , @Valid @RequestBody ProductDto productDto, HttpServletRequest request){
        try {
            if(username!=null && authService.isAuthorized(username)) {
                response = productService.createProduct(productDto);
                if (response == 200)
                    return responsBuilder("Product added successfully", request, HttpStatus.OK);
                else
                    return responsBuilder("Conflict in adding product", request, HttpStatus.CONFLICT);
            }else
                return responsBuilder("Forbidden request",request,HttpStatus.FORBIDDEN);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<ResponseDTO<String>> editProduct(
            @RequestHeader(value = "User-Agent") String username
            ,@Valid @RequestBody ProductDto productDto, HttpServletRequest request){
        try{
            if(username!=null && authService.isAuthorized(username)) {
                response = productService.editProduct(productDto);
                if (response == 200)
                    return responsBuilder("Product edited successfully", request, HttpStatus.OK);
                else
                    return responsBuilder("Product not found", request, HttpStatus.NOT_FOUND);
            }else
                return responsBuilder("Forbidden request",request,HttpStatus.FORBIDDEN);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO<String>> deleteProduct(
            @RequestHeader(value = "User-Agent") String username,
            @RequestParam("id") Integer productId, HttpServletRequest request){
        try{
            if(username!=null && authService.isAuthorized(username)) {
                response = productService.deleteProduct(productId);
                if (response == 200)
                    return responsBuilder("Product deleted successfully", request, HttpStatus.OK);
                else
                    return responsBuilder("Product not found", request, HttpStatus.NOT_FOUND);
            }else
                return responsBuilder("Forbidden request",request,HttpStatus.FORBIDDEN);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/get/all")
    public ResponseEntity<ResponseDTO<Object>> getAllProduct(HttpServletRequest request){
        try{
            List<ProductDto> productDtoList = productService.getAllProduct();
            if(productDtoList.isEmpty())
                return responsBuilder("Product list empty",request,HttpStatus.OK);
            else
                return responsBuilder(productDtoList,request,HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            throw ex;
        }
    }

    @PostMapping("/get/{productId}")
    public ResponseEntity<ResponseDTO<Object>> getProductById(
            @PathVariable("productId") int productId, HttpServletRequest request){
        try{
            ProductDto productDto = productService.getProductById(productId);
            return responsBuilder(productDto,request,HttpStatus.OK);
        }catch (Exception ex){
            throw ex;
        }
    }
}
