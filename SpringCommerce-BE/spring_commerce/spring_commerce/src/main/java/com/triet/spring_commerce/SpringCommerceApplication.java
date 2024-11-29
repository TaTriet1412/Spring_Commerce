package com.triet.spring_commerce;

import com.triet.spring_commerce.Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringCommerceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringCommerceApplication.class, args);
		UserService userService = applicationContext.getBean(UserService.class);
		userService.encodeOldPassword();
	}

}
