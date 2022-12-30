package restapi;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import restapi.model.ApiUser;
import restapi.repository.UserRepository;
import restapi.security.JwtTokenProvider;


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
      Random rand = new Random(); //instance of random class
      int upperbound = 2500;
      //generate random values from 0-2499
      int int_random = rand.nextInt(upperbound);
      ApiUser user = new ApiUser();
      user.setEmail(int_random + "@noel.de");
      user.setPassword(passwordEncoder.encode("1234"));

      ApiUser saved = userRepository.save(user);

      System.out.println("Token " + jwtTokenProvider.generateToken(saved.getEmail()));
    } catch (Exception e) {
      //
    }
  }
}