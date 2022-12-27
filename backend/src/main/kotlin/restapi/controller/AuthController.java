package restapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import restapi.jsonView.DataView;
import restapi.model.ApiUser;
import restapi.repository.UserRepository;
import restapi.data.AuthRequest;
import restapi.security.JwtTokenProvider;
// import gson to convert to JSON
import com.google.gson.Gson;

import javax.validation.Valid;
import org.json.JSONObject;

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
    public ResponseEntity<ApiUser> register(@Valid @RequestBody AuthRequest authRequest) {

        ApiUser userOptional = userRepository.findUserByEmail(authRequest.getEmail()); //ToDO: use userserice
        if (userOptional != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this Email already exists!");
        }

        ApiUser user = new ApiUser();
        user.setEmail(authRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        ApiUser created = userRepository.save(user);

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
/*
        // debug payload => transform to JSON
        String token = jwtTokenProvider.generateToken(authentication);

        final Gson gson = new Gson();

        String jsonString = "{\"jwt\":\"" + token + "\"}";
        System.out.println(jsonString);

        return ResponseEntity.ok(token);
        */

        ResponseEntity<String> token = ResponseEntity.ok(
            (jwtTokenProvider.generateToken(authentication)));
        System.out.println(token);
        return token;

    }
}
