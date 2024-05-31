package t1.openschool.jwtdemo.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import t1.openschool.jwtdemo.model.user.AppUser;
import t1.openschool.jwtdemo.model.user.AppUserDetails;
import t1.openschool.jwtdemo.repository.AppUserRepository;
import t1.openschool.jwtdemo.util.exceptions.AuthException;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetails findByLogin(String login) {
        AppUser appUser = appUserRepository.findByLogin(login)
                .orElseThrow(() -> new AuthException("User not found: " + login));

        return new AppUserDetails(appUser);
    }
}
