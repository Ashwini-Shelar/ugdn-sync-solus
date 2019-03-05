package com.upl.ugdnsyncsolus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = { "com.upl.ugdnsyncsolus" })
@MapperScan("com.upl.ugdnsyncsolus.mapper")
public class UgdnsyncsolusApplication {

	public static void main(String[] args) {
		SpringApplication.run(UgdnsyncsolusApplication.class, args);
	}

}
