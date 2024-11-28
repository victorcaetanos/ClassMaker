package utils.Validation;

import exceptions.ValidationException;

public class ProfessorValidation {
    public static void validateProfessorFields(Integer id, String name, String email, String phoneNumber, String cpf, String title) throws ValidationException {
        final String NAME = "Nome";
        final String TITLE = "Título";
        try {
            if (id != null) {
                ValidationUtils.validateId(id);
            }
            if (name != null) {
                ValidationUtils.validateString(name, NAME);
            }
            if (phoneNumber != null) {
                ValidationUtils.validatePhoneNumber(phoneNumber);
            }
            if (email != null) {
                ValidationUtils.validateEmail(email);
            }
            if (cpf != null) {
                ValidationUtils.validateCpf(cpf);
            }
            if (title != null) {
                ValidationUtils.validateString(title, TITLE);
            }
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
