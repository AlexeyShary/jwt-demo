package t1.openschool.jwtdemo.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t1.openschool.jwtdemo.model.RefreshToken;
import t1.openschool.jwtdemo.repository.RefreshTokenRepository;
import t1.openschool.jwtdemo.util.exceptions.AuthException;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    public RefreshToken save(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserId(userId);
        refreshToken.setValue(UUID.randomUUID().toString());
        refreshToken.setExpirationTime(Instant.now().plus(refreshTokenExpiration));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByValue(String refreshTokenValue) {
        RefreshToken result = refreshTokenRepository.findRefreshTokenByValue(refreshTokenValue)
                .orElseThrow(() -> new AuthException("Refresh token not found: " + refreshTokenValue));

        if (result.getExpirationTime().isBefore(Instant.now())) {
            throw new AuthException("Refresh token " + refreshTokenValue + " expired");
        }

        return result;
    }

    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(Instant.now());
    }
}
