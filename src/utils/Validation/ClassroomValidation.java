package utils.Validation;

import exceptions.ValidationException;

public class ClassroomValidation {
    public static void validateClassroomFields(Integer id, String name, Integer capacity, String location) throws ValidationException {
        String NAME = "Nome";
        String LOCATION = "Localização";
        try {
            if (id != null) {
                ValidationUtils.validateId(id);
            }
            if (capacity != null) {
                ValidationUtils.validateCapacity(capacity);
            }
            if (name != null) {
                ValidationUtils.validateString(name, NAME);
            }
            if (location != null) {
                ValidationUtils.validateString(location, LOCATION);
            }
        } catch (ValidationException error) {
            throw new ValidationException(error.getMessage());
        }
    }
}
