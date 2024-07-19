package br.com.example.trabalhobackend;

import br.com.example.trabalhobackend.service.EmailSenderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@OpenAPIDefinition(servers = {
    @Server(url = "/", description = "Default Server URL")
})
@RequiredArgsConstructor
@SpringBootApplication
public class TrabalhoBackendApplication {
    private final EmailSenderService senderService;

    public static void main(String[] args) {
        SpringApplication.run(TrabalhoBackendApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() {
        senderService.sendEmail("pedroleottebersch@gmail.com","forgot password","qualquer coisa bori texti \n texte de quabra de linha");
    }
}
