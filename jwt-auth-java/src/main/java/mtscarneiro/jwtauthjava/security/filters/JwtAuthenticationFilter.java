package mtscarneiro.jwtauthjava.security.filters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final String jwtSecret;
    private final long jwtExpirationTimeMillis;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecret, long jwtExpirationTimeMillis) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
        this.jwtExpirationTimeMillis = jwtExpirationTimeMillis;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTimeMillis))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }
}

