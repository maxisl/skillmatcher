package restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import restapi.model.ApiUser;
import restapi.repository.UserRepository;
import restapi.security.JwtTokenProvider;

import java.util.logging.Logger;


@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            ApiUser user = new ApiUser();
            user.setEmail("noel@noel.de");
            user.setPassword(passwordEncoder.encode("1234"));

            ApiUser saved = userRepository.save(user);

            System.out.println(jwtTokenProvider.generateToken(saved.getEmail()));
        } catch (Exception e) {
            //
        }
    }
}