package mtscarneiro.jwtauthjava.user.mapper;


import mtscarneiro.jwtauthjava.user.dto.UserDTO;
import mtscarneiro.jwtauthjava.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class UserMapper {

    public UserDTO toUserDto(User user) {
        HashMap<String, String> credentials = new HashMap<>();

        credentials.put("username", user.getUsername());
        credentials.put("password", user.getPassword());

        return new UserDTO(
                UUID.randomUUID().toString(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                credentials
        );
    }

    public User toUser(UserDTO user) {
        return new User(
                UUID.randomUUID().toString(),
                user.getFirstName(),
                user.getLastName(),
                user.getCellphone(),
                user.getCredentials().get("username"),
                user.getCredentials().get("password")
        );
    }
}