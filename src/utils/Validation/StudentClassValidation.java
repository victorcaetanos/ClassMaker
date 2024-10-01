package utils.Validation;

import exceptions.ValidationException;

public class StudentClassValidation {
    public static void validateStudentClassFields(Integer idStudent, Integer idClass) throws ValidationException {
        try {
            if (idStudent != null) {
                ValidationUtils.validateId(idStudent);
            }
            if (idClass != null) {
                ValidationUtils.validateId(idClass);
            }
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
