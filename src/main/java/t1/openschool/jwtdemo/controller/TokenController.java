package t1.openschool.jwtdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.openschool.jwtdemo.dto.security.AuthLoginRequestDto;
import t1.openschool.jwtdemo.dto.security.AuthRefreshRequestDto;
import t1.openschool.jwtdemo.dto.security.TokenResponseDto;
import t1.openschool.jwtdemo.service.security.SecurityService;

@RestController
@RequestMapping("/api/v1/public/token")
@RequiredArgsConstructor
public class TokenController {
    private final SecurityService securityService;

    @PostMapping("/login")
    public TokenResponseDto password(@RequestBody AuthLoginRequestDto authLoginRequestDto) {
        return securityService.processAuthLoginRequest(authLoginRequestDto);
    }

    @PostMapping("/refresh")
    public TokenResponseDto refresh(@RequestBody AuthRefreshRequestDto authRefreshRequestDto) {
        return securityService.processAuthRefreshRequest(authRefreshRequestDto);
    }
}
