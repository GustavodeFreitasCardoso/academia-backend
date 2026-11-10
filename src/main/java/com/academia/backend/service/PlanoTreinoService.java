package com.academia.backend.service;

import com.academia.backend.model.PlanoTreino;
import com.academia.backend.repository.PlanoTreinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanoTreinoService {

    private final AlunoService alunoService;
    private final PlanoTreinoRepository planoTreinoRepository;

    public PlanoTreinoService(
            AlunoService alunoService,
            PlanoTreinoRepository planoTreinoRepository
    ) {
        this.alunoService = alunoService;
        this.planoTreinoRepository = planoTreinoRepository;
    }

    public PlanoTreino salvar(PlanoTreino planoTreino) {
        alunoService.buscarAtivo(planoTreino.getAlunoId());
        return planoTreinoRepository.salvar(planoTreino);
    }

    public List<PlanoTreino> listarPorAluno(UUID alunoId) {
        alunoService.buscarAtivo(alunoId);
        return planoTreinoRepository.listarPorAlunoId(alunoId);
    }
}
