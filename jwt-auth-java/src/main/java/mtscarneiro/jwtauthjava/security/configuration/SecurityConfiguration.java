package mtscarneiro.jwtauthjava.security.configuration;

import mtscarneiro.jwtauthjava.security.entrypoint.UserAuthenticationEntryPoint;
import mtscarneiro.jwtauthjava.security.filters.CookieAuthenticationFilter;
import mtscarneiro.jwtauthjava.security.filters.UsernamePasswordAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration

public class SecurityConfiguration {

    @Value("${security.login.url}")
    private String LOGIN_URL;

    @Value("${security.register.url}")
    private String REGISTER_URL;

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    public SecurityConfiguration(UserAuthenticationEntryPoint userAuthenticationEntryPoint) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()

                .addFilterBefore(new UsernamePasswordAuthFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthenticationFilter(), UsernamePasswordAuthFilter.class)

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .logout()
                .deleteCookies(CookieAuthenticationFilter.COOKIE_NAME)
                .and()

                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, LOGIN_URL, REGISTER_URL).permitAll()
                .anyRequest().authenticated();

        return httpSecurity.build();
    }
}