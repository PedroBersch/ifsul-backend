package br.com.example.trabalhobackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {
    @Server(url = "/", description = "Default Server URL")
})
@RequiredArgsConstructor
@SpringBootApplication
public class TrabalhoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrabalhoBackendApplication.class, args);
    }

}
