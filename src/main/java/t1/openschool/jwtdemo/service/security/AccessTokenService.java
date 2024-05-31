package t1.openschool.jwtdemo.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import t1.openschool.jwtdemo.model.user.AppUserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessTokenService {
    private static final String ROLE_CLAIM = "role";
    private static final String ID_CLAIM = "id";

    @Value("${jwt.accessTokenSecret}")
    private String jwtSecret;

    @Value("${jwt.accessTokenExpiration}")
    private Duration tokenExpiration;

    private final AppUserDetailsService appUserDetailsService;

    public String generateToken(Long id, String login, List<String> roles) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration.toMillis()))
                .claim(ROLE_CLAIM, roles)
                .claim(ID_CLAIM, id)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public AppUserDetails toAppUserDetails(String token) {
        Claims tokenBody = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        String login = tokenBody.getSubject();
        return appUserDetailsService.findByLogin(login);
    }
}
