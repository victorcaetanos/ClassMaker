package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidTimeFormattingException extends ValidationException {
    public InvalidTimeFormattingException() {
        super("Horário com formatação incorreta!!");
    }

    public InvalidTimeFormattingException(String message) {
        super(message);
    }
}
