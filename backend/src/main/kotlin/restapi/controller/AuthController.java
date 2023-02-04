package restapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import restapi.jsonView.DataView;
import restapi.model.User;
import restapi.repository.UserRepository;
import restapi.data.AuthRequest;
import restapi.security.JwtTokenProvider;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @JsonView(DataView.User.class)
    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@Valid @RequestBody AuthRequest authRequest) {

        User userOptional = userRepository.findUserByEmail(authRequest.getEmail()); //ToDO: use userserice
        if (userOptional != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this Email already exists!");
        }

        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setImage(authRequest.getImage());

        User created = userRepository.save(user);

        return ResponseEntity.ok(created);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {

        Authentication authentication;
        authentication = authenticationManager.authenticate( // TODO: Custom return if login fails
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                ));

        ResponseEntity<String> token = ResponseEntity.ok(
            (jwtTokenProvider.generateToken(authentication)));
        return token;

    }
}
