package mtscarneiro.jwtauthjava.security.provider;

import mtscarneiro.jwtauthjava.authentication.AuthenticationService;
import mtscarneiro.jwtauthjava.authentication.login.dto.LoginDTO;
import mtscarneiro.jwtauthjava.user.dto.UserDTO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserAuthProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    public UserAuthProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDTO userDTO = null;

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            userDTO = authenticationService.authenticate(new LoginDTO(
                    (String) authentication.getPrincipal(),
                    (char[]) authentication.getCredentials())
            );

        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            userDTO = authenticationService.findByLogin((String) authentication.getPrincipal());
        }

        if (userDTO == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDTO, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}