package com.veigadealmeida.projetofinal.controller.customexceptions;


public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message) {
        super(message);
    }

}
