package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidStringFormattingException extends ValidationException {
    public InvalidStringFormattingException() {
        super("Texto com formatação incorreta!");
    }

    public InvalidStringFormattingException(String message) {
        super(message);
    }
}
