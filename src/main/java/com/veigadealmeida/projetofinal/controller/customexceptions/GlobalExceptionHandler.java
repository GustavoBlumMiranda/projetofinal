package com.veigadealmeida.projetofinal.controller.customexceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value= {ObjectNotFoundException.class})
    public ResponseEntity<Object> handleServiceException(ObjectNotFoundException ex, HandlerMethod handlerMethod) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        String errorMessage = "Erro ocorrido no método: " + handlerMethod.getMethod().getName() +
                ". Não foi possível realizar a operação. Erro:" + ex;

        ApiException apiException = new ApiException(
                errorMessage,
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
         return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value= {BadRequestException.class})
    public ResponseEntity<Object> handleServiceException(BadRequestException ex, HandlerMethod handlerMethod) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        String errorMessage = "Erro ocorrido no método: " + handlerMethod.getMethod().getName() +
                ". Não foi possível realizar a operação. Erro:" + ex;

        ApiException apiException = new ApiException(
                errorMessage,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
         return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value= {LoopException.class})
    public ResponseEntity<Object> handleServiceException(LoopException ex) {
        //TODO: Verificar se o status 409 é o mais adequado
        HttpStatus conflict = HttpStatus.CONFLICT;

        ApiException apiException = new ApiException(
                ex.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
         return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, HandlerMethod handlerMethod) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        String errorMessage = "Erro ocorrido no método: " + handlerMethod.getMethod().getName() +
                ". Não foi possível realizar a operação. Erro:" + ex;


        ApiException apiException = new ApiException(
                errorMessage,
                internalServerError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
         return new ResponseEntity<>(apiException, internalServerError);
    }

    @ExceptionHandler(value= {EntityNotFoundException.class})
    public ResponseEntity<Object> handleServiceException(EntityNotFoundException ex, HandlerMethod handlerMethod) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        String errorMessage = "Erro ocorrido no método: " + handlerMethod.getMethod().getName() +
                ". Não foi possível realizar a operação. Erro:" + ex;

        ApiException apiException = new ApiException(
                errorMessage,
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> notNullException(MethodArgumentNotValidException ex, HandlerMethod handlerMethod) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        String errorMessage = "Erro ocorrido no método: " + handlerMethod.getMethod().getName() +
                ". Não foi possível realizar a operação. Erros de validação: " + fieldErrors;

        ApiException apiException = new ApiException(
                errorMessage,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HandlerMethod handlerMethod) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        String errorMessage = "Erro ocorrido no método: " + handlerMethod.getMethod().getName() +
                ". Não foi possível realizar a operação. Erro: O corpo do objeto está vazio ou não é válido";

        ApiException apiException = new ApiException(
                errorMessage,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }
}
