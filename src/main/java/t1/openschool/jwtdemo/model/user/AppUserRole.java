package t1.openschool.jwtdemo.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_users_roles")
public class AppUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_user_role_name")
    private EAppUserRole name;
}