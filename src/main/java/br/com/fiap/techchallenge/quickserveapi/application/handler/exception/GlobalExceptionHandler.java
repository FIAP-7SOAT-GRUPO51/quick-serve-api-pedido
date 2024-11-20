package br.com.fiap.techchallenge.quickserveapi.application.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manipula NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        // Cria a resposta personalizada para o erro 404
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("details", "Not Found");
        // Retorna a resposta com status HTTP 404
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Manipula outras exceções não previstas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        // Cria a resposta personalizada para o erro 500
        Map<String, String> response = new HashMap<>();
        response.put("error", "[ERROR] - " + ex.getMessage());
        response.put("details", "Internal Server Error");
        // Retorna a resposta com status HTTP 500
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Trata IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
        // Cria a resposta personalizada para o erro 400
        Map<String, String> response = new HashMap<>();
        response.put("error", "Requisição inválida: " + ex.getMessage());
        response.put("details", "Bad Request");
        // Retorna a resposta com status HTTP 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
