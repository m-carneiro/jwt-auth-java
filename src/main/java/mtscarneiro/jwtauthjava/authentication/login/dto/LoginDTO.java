package mtscarneiro.jwtauthjava.authentication.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String username;
    private char[] password;

    public LoginDTO(String username, char[] password) {
        this.username = username;
        this.password = password;
    }
}
