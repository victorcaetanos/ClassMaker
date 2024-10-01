package exceptions.parsing;

import exceptions.ValidationException;

public class InvalidPhoneNumberException extends ValidationException {
    public InvalidPhoneNumberException() {
        super("Valor inválido para Telefone!");
    }

    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
