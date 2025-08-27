package co.com.pragma.model.exception;

public class FieldAlreadyRegisteredException extends RuntimeException{

    public FieldAlreadyRegisteredException(String fieldName) {
        super(fieldName + " ya está registrado");
    }
}
