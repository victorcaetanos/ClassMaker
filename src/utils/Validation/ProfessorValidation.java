package utils.Validation;

import exceptions.ValidationException;

public class ProfessorValidation {
    public static void validateProfessorFields(Integer id, String name, String email, String phoneNumber) throws ValidationException {
        String NAME = "Nome";
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
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
