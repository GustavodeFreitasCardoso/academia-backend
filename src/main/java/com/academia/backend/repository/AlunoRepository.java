package com.academia.backend.repository;

import com.academia.backend.model.Aluno;

import java.util.Optional;
import java.util.UUID;

public interface AlunoRepository {

    Aluno salvar(Aluno aluno);

    Optional<Aluno> buscarPorId(UUID alunoId);
}
