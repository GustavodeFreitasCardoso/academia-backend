package com.academia.backend.model;

import com.academia.backend.model.enums.GrupoMuscular;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlanoTreinoBuilderTest {

    @Test
    void deveConstruirPlanoComValoresPadraoEExercicio() {
        UUID alunoId = UUID.randomUUID();

        PlanoTreino plano = PlanoTreino.builder(alunoId, "Treino A", "Força")
                .adicionarExercicio(new Exercicio(
                        "Agachamento", GrupoMuscular.PERNAS, 3, 10
                ))
                .build();

        assertEquals(alunoId, plano.getAlunoId());
        assertEquals("Treino A", plano.getNome());
        assertEquals(3, plano.getFrequenciaSemanal());
        assertEquals("Nenhuma", plano.getObservacoes());
        assertEquals(1, plano.getExercicios().size());
    }

    @Test
    void deveImpedirAlteracaoDaListaDeExerciciosAposBuild() {
        PlanoTreino.Builder builder = PlanoTreino.builder(
                UUID.randomUUID(), "Treino A", "Força"
        );
        builder.adicionarExercicio(new Exercicio(
                "Agachamento", GrupoMuscular.PERNAS, 3, 10
        ));

        PlanoTreino plano = builder.build();
        builder.adicionarExercicio(new Exercicio(
                "Supino", GrupoMuscular.PEITO, 3, 10
        ));

        assertEquals(1, plano.getExercicios().size());
        assertThrows(
                UnsupportedOperationException.class,
                () -> plano.getExercicios().add(
                        new Exercicio("Remada", GrupoMuscular.COSTAS, 3, 10)
                )
        );
    }

    @Test
    void deveRejeitarFrequenciaSemanalForaDoIntervalo() {
        PlanoTreino.Builder builder = PlanoTreino.builder(
                UUID.randomUUID(), "Treino A", "Força"
        ).frequenciaSemanal(8);
        builder.adicionarExercicio(new Exercicio(
                "Agachamento", GrupoMuscular.PERNAS, 3, 10
        ));

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void deveRejeitarPlanoSemExercicios() {
        PlanoTreino.Builder builder = PlanoTreino.builder(
                UUID.randomUUID(), "Treino A", "Força"
        );

        assertThrows(IllegalStateException.class, builder::build);
    }
}
