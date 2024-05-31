package t1.openschool.jwtdemo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t1.openschool.jwtdemo.annotation.security.RequireAdminRole;
import t1.openschool.jwtdemo.dto.user.UserRequestPatchDto;
import t1.openschool.jwtdemo.dto.user.UserResponseFullDto;
import t1.openschool.jwtdemo.service.AppUserService;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
public class UserAdminController {
    private final AppUserService appUserService;

    @RequireAdminRole
    @PatchMapping("/{id}")
    @Operation(summary = "Patch user roles")
    public UserResponseFullDto patch(@PathVariable long id, @RequestBody UserRequestPatchDto userRequestPatchDto) {
        return appUserService.patch(id, userRequestPatchDto);
    }

    @RequireAdminRole
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        appUserService.delete(id);
    }
}
