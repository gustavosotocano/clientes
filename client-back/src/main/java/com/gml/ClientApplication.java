package com.gml;

import com.gml.entity.Client;
import com.gml.repository.ClientJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	CommandLineRunner init( ClientJpaRepository clientJpaRepository) {
		return args -> {
			Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
				Client client = new Client(name, name.toLowerCase() + "@domain.com",
						""+name.length(),new Date(),new Date(),new Date(),name,new Date());
				clientJpaRepository.save(client);
			});
			//clientJpaRepository.findAll().forEach(System.out::println);
		};
	}
}
