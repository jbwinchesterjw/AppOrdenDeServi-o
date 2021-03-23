package com.projetojava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EntityScan(basePackages = { "com.projetojava.domain" })
@ComponentScan(basePackages = { "com.*" })
@EnableJpaRepositories(basePackages = { "com.projetojava.repository" })
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
@EnableCaching
public class ProjetoJavaApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoJavaApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		/*registry.addMapping("/**")
		.allowedOrigins("*")
		.allowCredentials(false)
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");*/
		
		//registry.addMapping("http://localhost:4200").allowedMethods("*").allowedOrigins("*");
		
		registry.addMapping("/usuario/**").allowedMethods("*").allowedOrigins("*");

		registry.addMapping("/profissao/**").allowedMethods("*").allowedOrigins("*");

		registry.addMapping("/recuperar/**").allowedMethods("*").allowedOrigins("*");

	}
	/*
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html**")
				.addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.
				addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}*/

}
