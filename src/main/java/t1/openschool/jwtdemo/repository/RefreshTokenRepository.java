package t1.openschool.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import t1.openschool.jwtdemo.model.RefreshToken;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByValue(String value);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expirationTime <= :now")
    void deleteExpiredTokens(Instant now);
}
