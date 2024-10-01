package utils;

import exceptions.parsing.InvalidCapacityException;
import exceptions.parsing.InvalidIdException;
import exceptions.parsing.InvalidPhoneNumberException;
import exceptions.parsing.InvalidCpfException;

public class ParseUtils {

    public static Integer parseId(final String id) throws InvalidIdException {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        }
    }

    public static String parsePhoneNumber(final String phoneNumber) throws InvalidPhoneNumberException {

        final String regex = java.util.regex.Matcher.quoteReplacement("[() -]");
        final String replacement = "";
        try {
            return phoneNumber.replaceAll(regex, replacement);
        } catch (NumberFormatException e) {
            throw new InvalidPhoneNumberException();
        }
    }

    public static Integer parseCapacity(final String capacity) throws InvalidCapacityException {

        try {
            return Integer.parseInt(capacity);
        } catch (NumberFormatException e) {
            throw new InvalidCapacityException();
        }
    }

    public static String parseCpf(final String cpf) throws InvalidCpfException {

        final String regex = java.util.regex.Matcher.quoteReplacement("[.-]");
        final String replacement = "";
        try {
            return cpf.replaceAll(regex, replacement);
        } catch (NumberFormatException e) {
            throw new InvalidCpfException();
        }
    }
}

