package t1.openschool.jwtdemo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Register new user request")
public class UserRequestRegisterDto {
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
