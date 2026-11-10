package com.academia.backend.service;

import com.academia.backend.model.Aluno;
import com.academia.backend.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno cadastrar(String nome) {
        return alunoRepository.salvar(Aluno.novo(nome));
    }

    public Aluno buscarAtivo(UUID alunoId) {
        Aluno aluno = alunoRepository.buscarPorId(alunoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno não encontrado: " + alunoId
                ));

        if (!aluno.ativo()) {
            throw new IllegalStateException("O aluno está inativo e não pode realizar esta operação.");
        }

        return aluno;
    }
}
