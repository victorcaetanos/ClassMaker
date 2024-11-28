package utils.Validation;

import exceptions.formatting.*;

public class ValidationUtils {

    public static void validateId(final Integer id) throws InvalidIdFormattingException {
        final int MIN = 1;
        if (id == null || id < MIN) {
            throw new InvalidIdFormattingException("Por favor escolha um registro válido!");
        }
    }

    public static void validateString(final String string, final String title) throws InvalidStringFormattingException {
        if (string == null || string.isEmpty()) {
            throw new InvalidStringFormattingException(String.format("Por favor informe um %s!", title));
        }
    }

    public static void validatePhoneNumber(final String phoneNumber) throws InvalidPhoneNumberFormattingException {
        final int MIN = 10, MAX = 11;
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new InvalidPhoneNumberFormattingException("Por favor informe um Telefone!");
        } else if (!phoneNumber.matches("[0-9]+")) {
            throw new InvalidPhoneNumberFormattingException("Por favor informe um Telefone com apenas números!");
        } else if (phoneNumber.length() < MIN || phoneNumber.length() > MAX) {
            throw new InvalidPhoneNumberFormattingException(String.format("Por favor informe um Telefone com %s ou %s dígitos!", MIN, MAX));
        }
    }

    public static void validateCpf(final String cpf) throws InvalidCpfFormattingException {
        final int REQUIRED_SIZE = 11;
        if (cpf == null || cpf.isEmpty()) {
            throw new InvalidCpfFormattingException("Por favor informe um CPF!");
        } else if (!cpf.matches("[0-9]+")) {
            throw new InvalidCpfFormattingException("Por favor informe um CPF com apenas números!");
        } else if (cpf.length() != REQUIRED_SIZE) {
            throw new InvalidCpfFormattingException(String.format("Por favor informe um CPF com %s dígitos!", REQUIRED_SIZE));
        } else if (!validarCPF(cpf)) {
            throw new InvalidCpfFormattingException("Por favor informe um CPF valido");
        }
        //TODO: If needed, could check the CPF against its generation algorithm
    }

    public static boolean validarCPF(String cpf) {

        if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        int soma = 0;
        for (char c : cpf.toCharArray()) {
            soma += Character.getNumericValue(c);
        }

        return soma % 11 == 0 && soma / 11 <= 9;
    }

    public static void validateTime(final String time) throws InvalidTimeFormattingException {
        if (time == null || time.isEmpty()) {
            throw new InvalidTimeFormattingException("Por favor informe um Horário!");
        } else if (!time.matches("^\\d+:\\d+$")) {
            throw new InvalidTimeFormattingException("Por favor informe um Horário com apenas números!");
        } else if (time.compareTo("23:59") > 0) {
            throw new InvalidTimeFormattingException("Por favor informe um Horário menor que 24:00!");
        }
    }

    public static void validateSemester(final String semester) throws InvalidSemesterFormattingException {
        final int MAX = 7;
        if (semester == null || semester.isEmpty()) {
            throw new InvalidSemesterFormattingException("Por favor informe um Semestre!");
        } else if (semester.length() > MAX) {
            throw new InvalidSemesterFormattingException("Semestre inválido");
        } else if (!semester.matches("^\\d{4}/(01|02)$")) {
            throw new InvalidSemesterFormattingException("Por favor informe um Semestre com apenas números! Ex:'2024/01'");
        }
    }

    public static void validateWeekDay(final String weekDay) throws InvalidSemesterFormattingException {
        final int MAX = 13;
        if (weekDay == null || weekDay.isEmpty()) {
            throw new InvalidSemesterFormattingException("Por favor informe um Dia da Semana!");
        } else if (weekDay.length() > MAX) {
            throw new InvalidSemesterFormattingException("Palavra muito grande");
        }
    }

    public static void validateCapacity(final Integer capacity) throws InvalidCapacityFormattingException {
        final int MIN = 1;
        validateCapacity(capacity, MIN);
    }

    public static void validateCapacity(final Integer quantity, final int MIN) throws InvalidCapacityFormattingException {
        if (quantity == null || quantity < MIN) {
            throw new InvalidCapacityFormattingException(String.format("Por favor informe uma Capacidade maior que %s!", MIN - 1));
        }
    }

    public static void validateEmail(final String email) throws InvalidEmailFormattingException {
        final String regex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // More about the regex at https://regexr.com/3e48o
        if (!email.matches(regex)) {
            throw new InvalidEmailFormattingException("Por favor informe um Email válido!");
        }
    }

    public static void validateTimes(String startTime, String finishTime) throws InvalidTimeFormattingException {
        if (startTime.compareTo(finishTime) >= 0) {
            throw new InvalidTimeFormattingException("Por favor informe um Horário de Início menor que o Horário de Término!");
        }
    }
}
