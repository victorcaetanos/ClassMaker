package exceptions.parsing;

import exceptions.ValidationException;

public class InvalidCapacityException extends ValidationException {
    public InvalidCapacityException() {
        super("Valor inválido para Capacidade!");
    }

    public InvalidCapacityException(String message) {
        super(message);
    }
}
