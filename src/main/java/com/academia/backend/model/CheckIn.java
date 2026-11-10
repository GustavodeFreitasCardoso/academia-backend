package com.academia.backend.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record CheckIn(UUID id, UUID alunoId, Instant horario) {

    public CheckIn {
        Objects.requireNonNull(id, "O id do check-in é obrigatório.");
        Objects.requireNonNull(alunoId, "O aluno do check-in é obrigatório.");
        Objects.requireNonNull(horario, "O horário do check-in é obrigatório.");
    }

    public static CheckIn novo(UUID alunoId) {
        return new CheckIn(UUID.randomUUID(), alunoId, Instant.now());
    }
}
