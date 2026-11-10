package com.academia.backend.observer;

import com.academia.backend.model.CheckIn;

/** Contrato para qualquer comportamento que deva reagir a um check-in. */
@FunctionalInterface
public interface CheckInObserver {

    void aoRealizarCheckIn(CheckIn checkIn);
}
