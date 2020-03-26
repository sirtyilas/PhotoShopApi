package com.matyila.photoshop.gateway.PhotoShopZuulAPIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class PhotoShopZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoShopZuulApiGatewayApplication.class, args);
	}

}
