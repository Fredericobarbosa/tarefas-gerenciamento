package com.frederico.tarefasgerenciamento.service;

import com.frederico.tarefasgerenciamento.model.entity.Tarefa;
import com.frederico.tarefasgerenciamento.model.enums.StatusTarefa;

import java.util.List;
import java.util.Optional;

public interface TarefaService {

    Tarefa salvar(Tarefa tarefa);

    Tarefa atualizar(Long id, Tarefa tarefa);

    void deletar(Long id);

    Optional<Tarefa> buscarPorId(Long id);

    List<Tarefa> listarTodas();

    List<Tarefa> listarPorStatus(StatusTarefa status);
}
