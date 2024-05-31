package t1.openschool.jwtdemo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User operations response")
public class UserResponseFullDto {
    private Long id;
    private String login;
    private String password;
    private List<String> roles;
}
