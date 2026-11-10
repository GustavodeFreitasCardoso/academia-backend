package com.academia.backend.model;

import com.academia.backend.model.enums.GrupoMuscular;

import java.util.Objects;

public record Exercicio(
        String nome,
        GrupoMuscular grupoMuscular,
        int series,
        int repeticoes
) {

    public Exercicio {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do exercício é obrigatório.");
        }
        nome = nome.trim();
        Objects.requireNonNull(grupoMuscular, "O grupo muscular é obrigatório.");

        if (series <= 0) {
            throw new IllegalArgumentException("O exercício deve possuir ao menos uma série.");
        }
        if (repeticoes <= 0) {
            throw new IllegalArgumentException("O exercício deve possuir ao menos uma repetição.");
        }
    }
}
