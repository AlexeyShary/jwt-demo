package t1.openschool.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.openschool.jwtdemo.model.user.AppUserRole;
import t1.openschool.jwtdemo.model.user.EAppUserRole;

import java.util.Optional;

@Repository
public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {
    Optional<AppUserRole> findByName(EAppUserRole name);
}
