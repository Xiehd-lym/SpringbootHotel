package com.soft.demo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.soft.demo.dao")
@EnableTransactionManagement
public class TyypbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TyypbootApplication.class, args);
	}

}
