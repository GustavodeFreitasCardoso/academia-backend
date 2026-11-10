package com.academia.backend.repository;

import com.academia.backend.model.CheckIn;

import java.util.List;
import java.util.UUID;

public interface CheckInRepository {

    CheckIn salvar(CheckIn checkIn);

    List<CheckIn> listarPorAlunoId(UUID alunoId);
}
