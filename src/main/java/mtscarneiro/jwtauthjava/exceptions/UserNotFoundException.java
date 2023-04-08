package mtscarneiro.jwtauthjava.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userNotFound, HttpStatus notFound) {
        super(userNotFound);
    }
}
