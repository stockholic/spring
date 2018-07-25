package kr.zchat.core;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {Application.BASE_PACKAGES})
public class Application {
	
	public static final String BASE_PACKAGES = "kr.zchat";
	
	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
    }
    
}