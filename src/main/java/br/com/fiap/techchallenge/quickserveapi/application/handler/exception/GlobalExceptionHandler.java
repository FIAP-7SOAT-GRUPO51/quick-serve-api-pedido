package br.com.fiap.techchallenge.quickserveapi.application.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento para erros genéricos (500 - Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage(); // Captura a mensagem de erro
        System.err.println("Erro capturado: " + errorMessage); // Opcional: para depuração ou logs

        // Retorna a mensagem de erro para o cliente ou usa uma mensagem customizada
        return new ResponseEntity<>(
                "[ERROR] - " + errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Tratamento para erros específicos (400 - Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>("Requisição inválida: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
