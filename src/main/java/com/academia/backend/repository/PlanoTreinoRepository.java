package com.academia.backend.repository;

import com.academia.backend.model.PlanoTreino;

import java.util.List;
import java.util.UUID;

public interface PlanoTreinoRepository {

    PlanoTreino salvar(PlanoTreino planoTreino);

    List<PlanoTreino> listarPorAlunoId(UUID alunoId);
}
