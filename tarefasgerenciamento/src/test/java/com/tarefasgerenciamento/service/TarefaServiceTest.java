package com.frederico.tarefasgerenciamento.service;


import com.frederico.tarefasgerenciamento.exception.TarefaNaoEncontradaException;
import com.frederico.tarefasgerenciamento.model.entity.Tarefa;
import com.frederico.tarefasgerenciamento.model.enums.StatusTarefa;
import com.frederico.tarefasgerenciamento.model.repository.TarefaRepository;
import com.frederico.tarefasgerenciamento.service.impl.TarefaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - TarefaService")
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    private Tarefa tarefaBase;

    @BeforeEach
    void setUp() {
        tarefaBase = Tarefa.builder()
                .id(1L)
                .nome("Teste")
                .descricao("Efetuando teste de gerenciamento de tarefas")
                .status(StatusTarefa.PENDENTE)
                .observacoes("Obs tá funcionando")
                .build();
    }

    @Test
    @DisplayName("Deve salvar tarefa com sucesso")
    void deveSalvarTarefaComSucesso() {
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaBase);

        Tarefa salva = tarefaService.salvar(tarefaBase);

        assertThat(salva).isNotNull();
        assertThat(salva.getNome()).isEqualTo("Teste");
        verify(tarefaRepository, times(1)).save(tarefaBase);
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar tarefa sem nome")
    void deveLancarExcecaoAoSalvarTarefaSemNome() {
        Tarefa semNome = Tarefa.builder().descricao("Sem nome").status(StatusTarefa.PENDENTE).build();

        assertThatThrownBy(() -> tarefaService.salvar(semNome))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O nome da tarefa é obrigatório.");

        verify(tarefaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar tarefa com nome muito longo")
    void deveLancarExcecaoComNomeLongo() {
        String nomeLongo = "A".repeat(151);
        Tarefa tarefa = Tarefa.builder().nome(nomeLongo).status(StatusTarefa.PENDENTE).build();

        assertThatThrownBy(() -> tarefaService.salvar(tarefa))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O nome da tarefa não pode ter mais de 150 caracteres.");
    }

    @Test
    @DisplayName("Deve atualizar tarefa com sucesso")
    void deveAtualizarTarefaComSucesso() {
        Tarefa atualizada = Tarefa.builder()
                .nome("Nome Atualizado")
                .descricao("Descrição Atualizada")
                .status(StatusTarefa.EM_ANDAMENTO)
                .observacoes("Obs Atualizada")
                .build();

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaBase));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaBase);

        Tarefa resultado = tarefaService.atualizar(1L, atualizada);

        assertThat(resultado).isNotNull();
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar tarefa inexistente")
    void deveLancarExcecaoAoAtualizarTarefaInexistente() {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarefaService.atualizar(99L, tarefaBase))
                .isInstanceOf(TarefaNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Deve deletar tarefa com sucesso")
    void deveDeletarTarefaComSucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaBase));
        doNothing().when(tarefaRepository).delete(tarefaBase);

        assertThatCode(() -> tarefaService.deletar(1L)).doesNotThrowAnyException();

        verify(tarefaRepository, times(1)).delete(tarefaBase);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar tarefa inexistente")
    void deveLancarExcecaoAoDeletarTarefaInexistente() {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarefaService.deletar(99L))
                .isInstanceOf(TarefaNaoEncontradaException.class);
    }

    @Test
    @DisplayName("Deve buscar tarefa por ID com sucesso")
    void deveBuscarTarefaPorId() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaBase));

        Optional<Tarefa> encontrada = tarefaService.buscarPorId(1L);

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNome()).isEqualTo("Tarefa de Teste");
    }

    @Test
    @DisplayName("Deve listar todas as tarefas")
    void deveListarTodasAsTarefas() {
        when(tarefaRepository.findAll()).thenReturn(Arrays.asList(tarefaBase, tarefaBase));

        List<Tarefa> tarefas = tarefaService.listarTodas();

        assertThat(tarefas).hasSize(2);
    }

    @Test
    @DisplayName("Deve listar tarefas por status")
    void deveListarTarefasPorStatus() {
        when(tarefaRepository.findByStatus(StatusTarefa.PENDENTE))
                .thenReturn(List.of(tarefaBase));

        List<Tarefa> pendentes = tarefaService.listarPorStatus(StatusTarefa.PENDENTE);

        assertThat(pendentes).hasSize(1);
        assertThat(pendentes.get(0).getStatus()).isEqualTo(StatusTarefa.PENDENTE);
    }
}

