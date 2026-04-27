package com.platzi.play.domain.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long movieId) {
        super("La película con id [" + movieId + "] no existe.");
    }

    // Es posible tener dos constructores y permitir personalizar el mensaje de error.
    public MovieNotFoundException(String message) {
        super(message);
    }
}
