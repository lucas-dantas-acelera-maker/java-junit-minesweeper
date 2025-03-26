package br.com.aceleramaker.exception;

public class ExitException extends RuntimeException {
    public ExitException(String message) {
        super(message);
    }
}
