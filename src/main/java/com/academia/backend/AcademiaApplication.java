package com.academia.backend;

import com.academia.backend.client.AcademiaClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicação.
 *
 * O Spring inicializa os componentes e, em seguida, chama run(),
 * que delega a demonstração para o client de console.
 */
@SpringBootApplication
public class AcademiaApplication implements CommandLineRunner {

    private final AcademiaClient academiaClient;

    public AcademiaApplication(AcademiaClient academiaClient) {
        this.academiaClient = academiaClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(AcademiaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        academiaClient.executar();
    }
}
