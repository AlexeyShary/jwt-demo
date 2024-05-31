package t1.openschool.jwtdemo.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Tokens info response")
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
}
