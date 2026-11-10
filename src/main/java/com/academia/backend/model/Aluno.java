package com.academia.backend.model;

import java.util.Objects;
import java.util.UUID;

public record Aluno(UUID id, String nome, boolean ativo) {

    public Aluno {
        Objects.requireNonNull(id, "O id do aluno é obrigatório.");
        nome = validarNome(nome);
    }

    public static Aluno novo(String nome) {
        return new Aluno(UUID.randomUUID(), nome, true);
    }

    private static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }
        return nome.trim();
    }
}
