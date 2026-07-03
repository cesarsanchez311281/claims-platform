package com.cesar.claims;

import org.springframework.boot.SpringApplication;

public class TestClaimsPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.from(ClaimsPlatformApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
