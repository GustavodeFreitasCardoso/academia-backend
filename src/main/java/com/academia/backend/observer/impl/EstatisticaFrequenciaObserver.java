package com.academia.backend.observer.impl;

import com.academia.backend.model.CheckIn;
import com.academia.backend.observer.CheckInObserver;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

@Component
public class EstatisticaFrequenciaObserver implements CheckInObserver {

    private final ConcurrentMap<UUID, LongAdder> totalCheckInsPorAluno = new ConcurrentHashMap<>();

    @Override
    public void aoRealizarCheckIn(CheckIn checkIn) {
        LongAdder total = totalCheckInsPorAluno.computeIfAbsent(
                checkIn.alunoId(), id -> new LongAdder()
        );
        total.increment();

        System.out.printf(
                "[ESTATISTICA] Total de check-ins do aluno %s: %d.%n",
                checkIn.alunoId(), total.sum()
        );
    }

    public long consultarTotal(UUID alunoId) {
        LongAdder total = totalCheckInsPorAluno.get(alunoId);
        return total == null ? 0 : total.sum();
    }
}
