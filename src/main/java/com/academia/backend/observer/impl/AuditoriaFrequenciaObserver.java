package com.academia.backend.observer.impl;

import com.academia.backend.model.CheckIn;
import com.academia.backend.observer.CheckInObserver;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaFrequenciaObserver implements CheckInObserver {

    @Override
    public void aoRealizarCheckIn(CheckIn checkIn) {
        System.out.printf(
                "[AUDITORIA] Check-in %s registrado para o aluno %s em %s.%n",
                checkIn.id(), checkIn.alunoId(), checkIn.horario()
        );
    }
}
