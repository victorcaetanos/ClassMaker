package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidIdFormattingException extends ValidationException {
    public InvalidIdFormattingException() {
        super("ID com formatação incorreta!");
    }

    public InvalidIdFormattingException(String message) {
        super(message);
    }
}
