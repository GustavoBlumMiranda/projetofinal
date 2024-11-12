package com.veigadealmeida.projetofinal.controller.customexceptions;



public class LoopException extends RuntimeException{
    public LoopException(String message) {
        super(message);
    }
}
