package com.matyila.photoshop.discovery.PhotoShopAppDicoveryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PhotoShopAppDicoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoShopAppDicoveryServiceApplication.class, args);
	}

}
