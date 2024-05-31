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
@Schema(description = "Short user info")
public class UserResponseShortDto {
    private Long id;
    private String login;
    private List<String> roles;
}
