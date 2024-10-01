package utils.Validation;

import exceptions.ValidationException;

public class StudentValidation {
    public static void validateStudentFields(Integer id, String name, String cpf, String email, String phoneNumber, String address) throws ValidationException {
        String NAME = "Nome";
        String ADDRESS = "Endereço";
        try {
            if (id != null) {
                ValidationUtils.validateId(id);
            }
            if (name != null) {
                ValidationUtils.validateString(name, NAME);
            }
            if (cpf != null) {
                ValidationUtils.validateCpf(cpf);
            }
            if (email != null) {
                ValidationUtils.validateEmail(email);
            }
            if (phoneNumber != null) {
                ValidationUtils.validatePhoneNumber(phoneNumber);
            }
            if (address!= null) {
                ValidationUtils.validateString(address, ADDRESS);
            }
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
