package com.tigmaminds.ecommerce.repository;

import com.tigmaminds.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

//    @Query("SELECT COUNT(*) FROM PRODUCT p WHERE p.name = :name AND p.brand = :brand")
//    int countBySameNameAndBrand(@Param("name") String name,@Param("brand") String brand);
//
//    @Query("SELECT COUNT(*) FROM PRODUCT p WHERE p.id = :id",)
//    int countById(@Param("id") int id);
//    @Query(value = "SELECT COUNT(p) FROM PRODUCTENTITY p WHERE p.name = :name AND p.brand = :brand")
    @Query(value = "SELECT COUNT(*) FROM PRODUCT WHERE name = :name AND brand = :brand",nativeQuery = true)
    Integer countBySameNameAndBrand(@Param("name") String name,@Param("brand") String brand);
//
//    @Query("SELECT COUNT(*) FROM PRODUCT WHERE id = :id")
//    int countById(@Param("id") int id);
}
