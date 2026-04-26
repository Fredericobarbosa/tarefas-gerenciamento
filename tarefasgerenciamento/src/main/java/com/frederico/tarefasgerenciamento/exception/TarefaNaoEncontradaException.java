package com.frederico.tarefasgerenciamento.exception;

public class TarefaNaoEncontradaException extends RuntimeException {

    public TarefaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
