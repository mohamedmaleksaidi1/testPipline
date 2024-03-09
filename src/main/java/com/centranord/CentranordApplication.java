package com.centranord;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.centranord.*"})
@SpringBootApplication
public class CentranordApplication {



    public static void main(String[] args) {
		SpringApplication.run(CentranordApplication.class, args);




	}



}
