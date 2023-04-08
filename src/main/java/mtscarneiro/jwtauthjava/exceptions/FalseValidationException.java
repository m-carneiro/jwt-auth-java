package mtscarneiro.jwtauthjava.exceptions;

public class FalseValidationException extends RuntimeException {
    public FalseValidationException(String falseValidation) {
        super(falseValidation);
    }
}
