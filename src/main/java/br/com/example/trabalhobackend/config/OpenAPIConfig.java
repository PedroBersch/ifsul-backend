package br.com.example.trabalhobackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.apache.tomcat.websocket.BasicAuthenticator.schemeName;

@Configuration
public class OpenAPIConfig {
    private String rootUrl = "http://localhost:8080";

    @Bean
    public OpenAPI myOpenAPI() {

        String schemeName = "Bearer Token";
        String bearerFormat = "JWT";
        String scheme = "bearer";

        Server devServer = new Server();
        devServer.setUrl(rootUrl);
        devServer.setDescription("Server URL in Development environment");


        Contact contact = new Contact();
        contact.setEmail("pedroleottebersch@gmail.com");
        contact.setName("Pedro Leotte Bersch");
        contact.setUrl("https://www.google.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
            .title("Gerenciamento de produto")
            .version("1.0")
            .contact(contact)
            .description("Projeto com foco nas matérias de Serviços web e Frameworks de Backend.")
            .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer))
            .addSecurityItem(new SecurityRequirement()
                .addList(schemeName)).components(new Components()
                .addSecuritySchemes(
                    schemeName, new SecurityScheme()
                        .name(schemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat(bearerFormat)
                        .in(SecurityScheme.In.HEADER)
                        .scheme(scheme)
                )
            );
    }
}
