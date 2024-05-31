package t1.openschool.jwtdemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(name = "refresh_token_user_id")
    private Long userId;

    @Column(name = "refresh_token_value")
    private String value;

    @Column(name = "refresh_token_exp_time")
    private Instant expirationTime;
}
