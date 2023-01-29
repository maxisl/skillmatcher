package restapi.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest { //TODO: Replace AuthRequest with APIUser

    @NotBlank(message = "Email is mandatory")
    @SerializedName("email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @SerializedName("password")
    private String password;

    private String image;

}