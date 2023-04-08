package mtscarneiro.jwtauthjava.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String invalidLogin, HttpStatus badRequest) {
        super(invalidLogin);
    }
}
