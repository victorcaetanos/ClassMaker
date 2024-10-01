package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidCapacityFormattingException extends ValidationException {
    public InvalidCapacityFormattingException() {
        super("Capacidade com formatação incorreta!");
    }

    public InvalidCapacityFormattingException(String message) {
        super(message);
    }
}
