package com.product.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DemoProductSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProductSearchApplication.class, args);
	}

}
