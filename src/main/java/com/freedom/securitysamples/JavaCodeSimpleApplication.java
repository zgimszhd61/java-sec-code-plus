package com.freedom.securitysamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.freedom.securitysamples")
public class JavaCodeSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCodeSimpleApplication.class, args);
	}

}
