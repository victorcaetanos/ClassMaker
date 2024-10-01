package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidCpfFormattingException extends ValidationException {
    public InvalidCpfFormattingException() {
        super("CPF com formatação incorreta!!");
    }

    public InvalidCpfFormattingException(String message) {
        super(message);
    }
}
