package com.hansung.leafly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class LeaflyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaflyApplication.class, args);
	}

}
