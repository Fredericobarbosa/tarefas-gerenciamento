package com.frederico.tarefasgerenciamento.model.repository;

import com.frederico.tarefasgerenciamento.model.entity.Tarefa;
import com.frederico.tarefasgerenciamento.model.enums.StatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByStatus(StatusTarefa status);

    List<Tarefa> findByNomeContainingIgnoreCase(String nome);
}

