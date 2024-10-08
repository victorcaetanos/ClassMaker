package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidSemesterFormattingException extends ValidationException {
    public InvalidSemesterFormattingException() {
        super("Semestre com formatação incorreta!");
    }

    public InvalidSemesterFormattingException(String message) {
        super(message);
    }
}
