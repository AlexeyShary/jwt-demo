package t1.openschool.jwtdemo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import t1.openschool.jwtdemo.model.user.EAppUserRole;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Admin request to change user roles")
public class UserRequestPatchDto {
    @Builder.Default
    private List<EAppUserRole> roles = new ArrayList<>();
}
