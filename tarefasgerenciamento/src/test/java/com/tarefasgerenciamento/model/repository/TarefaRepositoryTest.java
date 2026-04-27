package com.frederico.tarefasgerenciamento.model.repository;


import com.frederico.tarefasgerenciamento.model.entity.Tarefa;
import com.frederico.tarefasgerenciamento.model.enums.StatusTarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Testes de Integração - TarefaRepository")
class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @BeforeEach
    void setUp() {
        tarefaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar uma tarefa com sucesso")
    void deveSalvarTarefaComSucesso() {
        Tarefa tarefa = criarTarefa("Estudar Spring Boot", StatusTarefa.PENDENTE);

        Tarefa salva = tarefaRepository.save(tarefa);

        assertThat(salva).isNotNull();
        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getNome()).isEqualTo("Estudar Spring Boot");
        assertThat(salva.getStatus()).isEqualTo(StatusTarefa.PENDENTE);
    }

    @Test
    @DisplayName("Deve buscar tarefa por ID")
    void deveBuscarTarefaPorId() {
        Tarefa tarefa = tarefaRepository.save(criarTarefa("Tarefa teste", StatusTarefa.PENDENTE));

        Optional<Tarefa> encontrada = tarefaRepository.findById(tarefa.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNome()).isEqualTo("Tarefa teste");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para ID inexistente")
    void deveRetornarVazioParaIdInexistente() {
        Optional<Tarefa> encontrada = tarefaRepository.findById(999L);

        assertThat(encontrada).isEmpty();
    }


    @Test
    @DisplayName("Deve listar tarefas por status")
    void deveListarTarefasPorStatus() {
        tarefaRepository.save(criarTarefa("Tarefa 1", StatusTarefa.PENDENTE));
        tarefaRepository.save(criarTarefa("Tarefa 2", StatusTarefa.PENDENTE));
        tarefaRepository.save(criarTarefa("Tarefa 3", StatusTarefa.CONCLUIDA));

        List<Tarefa> pendentes = tarefaRepository.findByStatus(StatusTarefa.PENDENTE);

        assertThat(pendentes).hasSize(2);
        assertThat(pendentes).allMatch(t -> t.getStatus() == StatusTarefa.PENDENTE);
    }

    @Test
    @DisplayName("Deve deletar uma tarefa")
    void deveDeletarTarefa() {
        Tarefa tarefa = tarefaRepository.save(criarTarefa("Deletar", StatusTarefa.PENDENTE));
        Long id = tarefa.getId();

        tarefaRepository.delete(tarefa);

        assertThat(tarefaRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todas as tarefas")
    void deveListarTodasAsTarefas() {
        tarefaRepository.save(criarTarefa("Tarefa A", StatusTarefa.PENDENTE));
        tarefaRepository.save(criarTarefa("Tarefa B", StatusTarefa.EM_ANDAMENTO));

        List<Tarefa> todas = tarefaRepository.findAll();

        assertThat(todas).hasSize(2);
    }

    private Tarefa criarTarefa(String nome, StatusTarefa status) {
        return Tarefa.builder()
                .nome(nome)
                .descricao("Descrição da tarefa")
                .status(status)
                .observacoes("Observação de teste")
                .build();
    }
}

