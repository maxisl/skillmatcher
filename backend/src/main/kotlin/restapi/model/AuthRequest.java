package restapi.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest { //TODO: Replace AuthRequest with APIUser

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

}