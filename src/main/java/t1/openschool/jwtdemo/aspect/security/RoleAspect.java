package t1.openschool.jwtdemo.aspect.security;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import t1.openschool.jwtdemo.model.user.EAppUserRole;
import t1.openschool.jwtdemo.util.exceptions.AuthException;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAspect {
    @Before("@annotation(t1.openschool.jwtdemo.annotation.security.RequireAdminRole)")
    public void checkAdminRole() {
        if (!checkRole(EAppUserRole.ROLE_ADMIN.name())) {
            throw new AuthException("Not admin");
        }
    }

    @Before("@annotation(t1.openschool.jwtdemo.annotation.security.RequireAuthorRole)")
    public void checkAuthorRole() {
        if (!checkRole(EAppUserRole.ROLE_AUTHOR.name())) {
            throw new AuthException("Not author");
        }
    }

    @Before("@annotation(t1.openschool.jwtdemo.annotation.security.RequireUserRole)")
    public void checkUserRole() {
        if (!checkRole(EAppUserRole.ROLE_USER.name())) {
            throw new AuthException("Not user");
        }
    }

    private boolean checkRole(String roleName) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        var authorities = authentication.getAuthorities();
        return authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}
