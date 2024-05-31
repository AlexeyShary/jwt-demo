package t1.openschool.jwtdemo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.openschool.jwtdemo.annotation.security.RequireUserRole;
import t1.openschool.jwtdemo.dto.user.UserResponseShortDto;
import t1.openschool.jwtdemo.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/user")
@RequiredArgsConstructor
public class UserPrivateController {
    private final AppUserService appUserService;

    @RequireUserRole
    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserResponseShortDto> getAll() {
        return appUserService.getAll();
    }

    @RequireUserRole
    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public UserResponseShortDto getById(@PathVariable long id) {
        return appUserService.getById(id);
    }
}
