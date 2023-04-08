package mtscarneiro.jwtauthjava.exceptions;

import lombok.Data;

@Data
public class ErrorDTO {
    String message;

    public ErrorDTO(String message) {
        this.message = message;
    }
}
