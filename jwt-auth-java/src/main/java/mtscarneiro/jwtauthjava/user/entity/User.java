package mtscarneiro.jwtauthjava.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_users")
public class User implements Serializable {

        @Id
        private String id;
        private String username;
        private String password;
        private String email;
        private String name;
        private String lastName;
        private String role;

        public User(String id, String name, String lastName, String email, String username, String password) {
                this.id = id;
                this.name = name;
                this.lastName = lastName;
                this.email = email;
                this.username = username;
                this.password = password;
        }
}
