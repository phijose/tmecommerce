package com.tigmaminds.ecommerce;

import com.tigmaminds.ecommerce.dao.UsersDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
//
//		UsersDao usersDao = context.getBean(UsersDao.class);
//
//		usersDao.getUserByUsernameAndPassword("phijose@gmail","password2");
//		ProductEntity prod1 = new ProductEntity().builder()
//				.brand("Pixel").price(37999).name("Pixel 7a").category("Smart Phone").build();
//
//		ProductRepository repository = context.getBean(ProductRepository.class);
//
//		System.out.println(repository.save(prod1));
//
//		List<ProductEntity> productEntityList = repository.findAll();
//
//		productEntityList.forEach((productEntity ->
//				System.out.println(productEntity.getName()+" : "+productEntity.getPrice())));

	}
}
