package utils.Validation;

import exceptions.ValidationException;

public class MyClassValidation {
    public static void validateMyClassFields(Integer id, Integer professorId, Integer disciplineId, Integer classroomId, String startTime, String finishTime, String semester) throws ValidationException {
        try {
            if (id != null) {
                ValidationUtils.validateId(id);
            }
            if (id != null) {
                ValidationUtils.validateId(professorId);
            }
            if (id != null) {
                ValidationUtils.validateId(disciplineId);
            }
            if (id != null) {
                ValidationUtils.validateId(classroomId);
            }
            if (startTime != null) {
                ValidationUtils.validateTime(startTime);
            }
            if (finishTime != null) {
                ValidationUtils.validateTime(finishTime);
            }
            if (startTime != null && finishTime != null) {
                ValidationUtils.validateTimes(startTime, finishTime);
            }
            if (semester != null) {
                ValidationUtils.validateSemester(semester);
            }
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
