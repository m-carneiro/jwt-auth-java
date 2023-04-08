package mtscarneiro.jwtauthjava.authentication;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mtscarneiro.jwtauthjava.authentication.login.dto.LoginDTO;
import mtscarneiro.jwtauthjava.exceptions.FalseValidationException;
import mtscarneiro.jwtauthjava.exceptions.InvalidLoginException;
import mtscarneiro.jwtauthjava.exceptions.UserNotFoundException;
import mtscarneiro.jwtauthjava.user.dto.UserDTO;
import mtscarneiro.jwtauthjava.user.entity.User;
import mtscarneiro.jwtauthjava.user.mapper.UserMapper;
import mtscarneiro.jwtauthjava.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Service
@Slf4j
public class AuthenticationService {

    @Value("${jwt.secret}")
    private String ALGORITHM_VALUE;

    @Value("${jwt.invalid-cookie}")
    private String INVALID_COOKIE;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO authenticate(LoginDTO credentials) {
        User user = userRepository.getByUsername(credentials.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentials.getPassword()), user.getPassword())) {
            log.debug("User {} authenticated correctly", credentials.getUsername());
            return userMapper.toUserDto(user);
        }
        throw new InvalidLoginException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDTO findByLogin(String username) {
        User user = userRepository.getByUsername(username)
                .orElseThrow(() -> new InvalidLoginException("Login not found", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public String createToken(UserDTO userDTO) {
        return userDTO.getId() + "&" + userDTO.getCredentials().get("username") + "&" + calculateHmac(userDTO);
    }

    public UserDTO findByToken(String token) {
        String[] parts = token.split("&");

        Long userID = Long.valueOf(parts[0]);
        String username = parts[1];
        String hmac = parts[2];

        UserDTO user = findByLogin(username);

        if (!hmac.equals(calculateHmac(user))) {
            throw new FalseValidationException(INVALID_COOKIE);
        }

        if (!userID.equals(user.getId())) {
            throw new FalseValidationException(INVALID_COOKIE);
        }

        return user;
    }

    private String calculateHmac(UserDTO userDTO) {
        byte[] secretKeyBytes = Objects.requireNonNull(userDTO.getCredentials().get("password"))
                .getBytes(StandardCharsets.UTF_8);

        byte[] valueBytes = (userDTO.getId() + "&" + userDTO.getCredentials().get("username"))
                .getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance(ALGORITHM_VALUE);
            SecretKeySpec SKS = new SecretKeySpec(secretKeyBytes, ALGORITHM_VALUE);
            mac.init(SKS);

            byte[] hmacBytes = mac.doFinal(valueBytes);

            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new FalseValidationException(e.getMessage());
        }
    }
}