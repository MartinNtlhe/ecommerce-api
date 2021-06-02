package com.example.ecommerce;

import com.example.ecommerce.entity.Type;
import com.example.ecommerce.entity.UserType;
import com.example.ecommerce.resource.IUserTypeRepository;
import com.example.ecommerce.service.bean.UserTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EcommerceApplication {

	@Autowired
	private UserTypeServiceImpl userTypeServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}
