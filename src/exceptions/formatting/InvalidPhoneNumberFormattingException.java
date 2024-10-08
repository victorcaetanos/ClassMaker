package exceptions.formatting;

import exceptions.ValidationException;

public class InvalidPhoneNumberFormattingException extends ValidationException {
    public InvalidPhoneNumberFormattingException() {
        super("Telefone com formatação incorreta!");
    }

    public InvalidPhoneNumberFormattingException(String message) {
        super(message);
    }
}
