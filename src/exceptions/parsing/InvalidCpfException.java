package exceptions.parsing;

import exceptions.ValidationException;

public class InvalidCpfException extends ValidationException {
    public InvalidCpfException() {
        super("Valor inválido para CPF!");
    }

    public InvalidCpfException(String message) {
        super(message);
    }
}
