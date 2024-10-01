package exceptions.parsing;

import exceptions.ValidationException;

public class InvalidIdException extends ValidationException {
    public InvalidIdException() {
        super("Valor inválido para ID!");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
