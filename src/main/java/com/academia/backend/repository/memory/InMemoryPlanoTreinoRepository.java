package com.academia.backend.repository.memory;

import com.academia.backend.model.PlanoTreino;
import com.academia.backend.repository.PlanoTreinoRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryPlanoTreinoRepository implements PlanoTreinoRepository {

    private final ConcurrentMap<UUID, PlanoTreino> planos = new ConcurrentHashMap<>();

    @Override
    public PlanoTreino salvar(PlanoTreino planoTreino) {
        planos.put(planoTreino.getId(), planoTreino);
        return planoTreino;
    }

    @Override
    public List<PlanoTreino> listarPorAlunoId(UUID alunoId) {
        return planos.values().stream()
                .filter(plano -> plano.getAlunoId().equals(alunoId))
                .sorted(Comparator.comparing(PlanoTreino::getDataCriacao))
                .toList();
    }
}
