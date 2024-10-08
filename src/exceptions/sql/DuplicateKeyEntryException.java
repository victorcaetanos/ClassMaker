package exceptions.sql;

import java.sql.SQLException;

public class DuplicateKeyEntryException extends SQLException {
    public DuplicateKeyEntryException() {
        super("Registro já existe!");
    }

    public DuplicateKeyEntryException(String message) {
        super(message);
    }
}
