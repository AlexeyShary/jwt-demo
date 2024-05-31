package t1.openschool.jwtdemo.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t1.openschool.jwtdemo.dto.user.UserRequestRegisterDto;
import t1.openschool.jwtdemo.dto.user.UserResponseFullDto;
import t1.openschool.jwtdemo.service.AppUserService;

@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class UserPublicController {
    private final AppUserService appUserService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseFullDto register(@RequestBody UserRequestRegisterDto userRequestRegisterDto) {
        return appUserService.create(userRequestRegisterDto);
    }
}
