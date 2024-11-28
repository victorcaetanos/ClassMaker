package utils.Validation;

import exceptions.ValidationException;

public class DisciplineValidation {
    public static void validateDisciplineFields(Integer id, String name, String code, String description, String periodo) throws ValidationException {
        String NAME = "Nome";
        String CODE = "Código";
        String DESCRIPTION = "Descrição";
        String PERIODO = "Periodo";
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
            if (description != null) {
                ValidationUtils.validateString(description, DESCRIPTION);
            }
            if (periodo != null) {
                ValidationUtils.validateString(description, PERIODO);
            }
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
