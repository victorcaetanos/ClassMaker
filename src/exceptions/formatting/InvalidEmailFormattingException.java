package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidEmailFormattingException extends ValidationException {
    public InvalidEmailFormattingException() {
        super("Email com formatação incorreta!");
    }

    public InvalidEmailFormattingException(String message) {
        super(message);
    }
}
