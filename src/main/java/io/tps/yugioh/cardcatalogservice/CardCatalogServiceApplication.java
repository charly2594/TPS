package io.tps.yugioh.cardcatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class CardCatalogServiceApplication {
	@RequestMapping("/")
	public String home() {
		return "Card Catalog Resource";
	}
	public static void main(String[] args) {
		SpringApplication.run(CardCatalogServiceApplication.class, args);
	}

}
