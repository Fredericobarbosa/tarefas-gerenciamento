package com.frederico.tarefasgerenciamento.api.resource;

import com.frederico.tarefasgerenciamento.exception.TarefaNaoEncontradaException;
import com.frederico.tarefasgerenciamento.model.entity.Tarefa;
import com.frederico.tarefasgerenciamento.model.enums.StatusTarefa;
import com.frederico.tarefasgerenciamento.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaResource {

	@Autowired
	private TarefaService tarefaService;

	@PostMapping
	public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa) {
		Tarefa tarefaSalva = tarefaService.salvar(tarefa);
		return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
		Tarefa tarefaAtualizada = tarefaService.atualizar(id, tarefa);
		return ResponseEntity.ok(tarefaAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		tarefaService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
		return tarefaService.buscarPorId(id).map(ResponseEntity::ok)
				.orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada com o id: " + id));
	}

	@GetMapping
	public ResponseEntity<List<Tarefa>> listar(@RequestParam(required = false) StatusTarefa status) {

		List<Tarefa> tarefas = (status != null) ? tarefaService.listarPorStatus(status) : tarefaService.listarTodas();

		return ResponseEntity.ok(tarefas);
	}
}
