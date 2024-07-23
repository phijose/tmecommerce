package com.tigmaminds.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    int id;
    @Size(min = 2,message = "Invalid brand name")
    @Pattern(regexp = "^.*[a-zA-Z]$",message = "Invalid brand name")
    String brand;
    @Pattern(regexp = "^.*[a-z0-9A-Z]$",message = "Invalid product name")
    String name;
    @Size(min = 3,message = "Invalid Category")
    @Pattern(regexp = "^.*[a-zA-Z]$",message = "Invalid Category")
    String category;
    @Min(value=10,message = "Invalid price")
    int price;

    public ProductDto(String brand, String name, String category, int price) {
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.price = price;
    }
}
