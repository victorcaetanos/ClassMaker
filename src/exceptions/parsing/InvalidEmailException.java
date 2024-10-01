package exceptions.parsing;

import exceptions.ValidationException;

public class InvalidEmailException extends ValidationException {
    public InvalidEmailException() {
        super("Valor inválido para Email!");
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
