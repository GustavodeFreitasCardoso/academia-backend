package com.academia.backend.repository.memory;

import com.academia.backend.model.Aluno;
import com.academia.backend.repository.AlunoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryAlunoRepository implements AlunoRepository {

    private final ConcurrentMap<UUID, Aluno> alunos = new ConcurrentHashMap<>();

    @Override
    public Aluno salvar(Aluno aluno) {
        alunos.put(aluno.id(), aluno);
        return aluno;
    }

    @Override
    public Optional<Aluno> buscarPorId(UUID alunoId) {
        return Optional.ofNullable(alunos.get(alunoId));
    }
}
