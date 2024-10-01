package utils.Validation;

import exceptions.ValidationException;

public class DisciplineValidation {
    public static void validateDisciplineFields(Integer id, String name, String code, String location) throws ValidationException {
        String NAME = "Nome";
        String CODE = "Código";
        String DESCRIPTION = "Descrição";
        try {
            if (id != null) {
                ValidationUtils.validateId(id);
            }
            if (code != null) {
                ValidationUtils.validateString(code, NAME);
            }
            if (name != null) {
                ValidationUtils.validateString(name, CODE);
            }
            if (location != null) {
                ValidationUtils.validateString(location, DESCRIPTION);
            }
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
