package restapi;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import restapi.model.Skill;
import restapi.repository.SkillRepository;
import restapi.repository.UserRepository;
import restapi.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

  @Autowired
  private SkillRepository skillRepository;

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
    System.out.println("version: " + SpringVersion.getVersion());

    try {
      List<String> Skills = Arrays.asList("Cloud", "AI", "React", "Java", "Python", "Scrum", "IoT",
          "R", "Web3", "C");
      if (skillRepository.count() <= 0) {
        for (String name : Skills) {
          // insert predefined skills in skills table
          Skill skill = new Skill();
          skill.setName(name);
          skillRepository.save(skill);
        }
      }

    } catch (Exception e) {
      log.error("Error: ", e);
    }
  }
}