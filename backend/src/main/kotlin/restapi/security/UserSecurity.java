package restapi.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;
@Slf4j
@Component("userSecurity")
public class UserSecurity {
    public boolean hasUserEmail(Authentication authentication, String userMail) {
        log.error("User: " + userMail + " tries to log in with token: " + authentication.getName());

        if (userMail.equals(authentication.getName())) {
            return true;
        } else {
            return false;
        }
    }
}
