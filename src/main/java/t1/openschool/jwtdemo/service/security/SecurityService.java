package t1.openschool.jwtdemo.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import t1.openschool.jwtdemo.dto.security.AuthLoginRequestDto;
import t1.openschool.jwtdemo.dto.security.AuthRefreshRequestDto;
import t1.openschool.jwtdemo.dto.security.TokenResponseDto;
import t1.openschool.jwtdemo.model.user.AppUser;
import t1.openschool.jwtdemo.service.AppUserService;
import t1.openschool.jwtdemo.util.exceptions.AuthException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AppUserService appUserService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    public TokenResponseDto processAuthLoginRequest(AuthLoginRequestDto dto) {
        AppUser appUser = appUserService.findAppUserByLogin(dto.getLogin());
        if (!passwordEncoder.matches(dto.getPassword(), appUser.getPassword())) {
            throw new AuthException("Login failed");
        }

        return createTokens(appUser);
    }

    public TokenResponseDto processAuthRefreshRequest(AuthRefreshRequestDto dto) {
        Long appUserId = refreshTokenService.findByValue(dto.getRefreshToken()).getUserId();
        AppUser appUser = appUserService.findAppUserById(appUserId);
        return createTokens(appUser);
    }

    private TokenResponseDto createTokens(AppUser appUser) {
        String accessToken = accessTokenService.generateToken(appUser.getId(), appUser.getLogin(),
                appUser.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()));

        String refreshToken = refreshTokenService.save(appUser.getId()).getValue();

        return new TokenResponseDto().builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
