package t1.openschool.jwtdemo.dto.security;

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
@Schema(description = "Token request by login and password")
public class AuthLoginRequestDto {
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
