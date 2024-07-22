package com.tigmaminds.ecommerce;

import com.tigmaminds.ecommerce.dao.UsersDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
//
//		UsersDao usersDao = context.getBean(UsersDao.class);
//
//		usersDao.getUserByUsernameAndPassword("phijose@gmail","password2");
	}

}
