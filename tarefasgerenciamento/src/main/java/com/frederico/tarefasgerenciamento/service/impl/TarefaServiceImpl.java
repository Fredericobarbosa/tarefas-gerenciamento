package com.frederico.tarefasgerenciamento.service.impl;

import com.frederico.tarefasgerenciamento.exception.TarefaNaoEncontradaException;
import com.frederico.tarefasgerenciamento.model.entity.Tarefa;
import com.frederico.tarefasgerenciamento.model.enums.StatusTarefa;
import com.frederico.tarefasgerenciamento.model.repository.TarefaRepository;
import com.frederico.tarefasgerenciamento.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Override
    @Transactional
    public Tarefa salvar(Tarefa tarefa) {
        validarTarefa(tarefa);
        return tarefaRepository.save(tarefa);
    }

    @Override
    @Transactional
    public Tarefa atualizar(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada com o id: " + id));

        validarTarefa(tarefaAtualizada);

        tarefaExistente.setNome(tarefaAtualizada.getNome());
        tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
        tarefaExistente.setStatus(tarefaAtualizada.getStatus());
        tarefaExistente.setObservacoes(tarefaAtualizada.getObservacoes());

        return tarefaRepository.save(tarefaExistente);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada com o id: " + id));
        tarefaRepository.delete(tarefa);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarefa> listarPorStatus(StatusTarefa status) {
        return tarefaRepository.findByStatus(status);
    }


    private void validarTarefa(Tarefa tarefa) {
        if (tarefa.getNome() == null || tarefa.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da tarefa é obrigatório.");
        }
        if (tarefa.getNome().length() > 150) {
            throw new IllegalArgumentException("O nome da tarefa não pode ter mais de 150 caracteres.");
        }
    }
}