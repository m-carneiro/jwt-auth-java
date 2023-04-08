package mtscarneiro.jwtauthjava.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String cellphone;
    private HashMap<String, String> credentials;

    public UserDTO(String id, String firstName, String lastName, String cellphone, HashMap<String, String> credentials) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellphone = cellphone;
        this.credentials = credentials;
    }
}
