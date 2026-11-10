package com.academia.backend.observer.impl;

import com.academia.backend.model.CheckIn;
import com.academia.backend.observer.CheckInObserver;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoObserver implements CheckInObserver {

    @Override
    public void aoRealizarCheckIn(CheckIn checkIn) {
        System.out.printf(
                "[NOTIFICACAO] O aluno %s realizou um check-in.%n",
                checkIn.alunoId()
        );
    }
}
