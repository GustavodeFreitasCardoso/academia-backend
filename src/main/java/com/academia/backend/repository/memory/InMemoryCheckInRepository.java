package com.academia.backend.repository.memory;

import com.academia.backend.model.CheckIn;
import com.academia.backend.repository.CheckInRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryCheckInRepository implements CheckInRepository {

    private final ConcurrentMap<UUID, CheckIn> checkIns = new ConcurrentHashMap<>();

    @Override
    public CheckIn salvar(CheckIn checkIn) {
        checkIns.put(checkIn.id(), checkIn);
        return checkIn;
    }

    @Override
    public List<CheckIn> listarPorAlunoId(UUID alunoId) {
        return checkIns.values().stream()
                .filter(checkIn -> checkIn.alunoId().equals(alunoId))
                .sorted(Comparator.comparing(CheckIn::horario))
                .toList();
    }
}
