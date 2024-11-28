package utils.Validation;

import exceptions.ValidationException;

public class MyClassValidation {
    public static void validateMyClassFields(Integer id, Integer professorId, Integer disciplineId, Integer classroomId, String weekDay, String startTime, String finishTime, String semester) throws ValidationException {
        final String WEEKDAY = "Dia da semana";

        try {
            if (id != null) {
                ValidationUtils.validateId(id);
            }
            if (professorId != null) {
                ValidationUtils.validateId(professorId);
            }
            if (disciplineId != null) {
                ValidationUtils.validateId(disciplineId);
            }
            if (classroomId != null) {
                ValidationUtils.validateId(classroomId);
            }
            if (weekDay != null) {
                ValidationUtils.validateWeekDay(weekDay);
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
