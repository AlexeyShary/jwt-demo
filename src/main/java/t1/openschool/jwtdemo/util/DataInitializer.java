package t1.openschool.jwtdemo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import t1.openschool.jwtdemo.model.News;
import t1.openschool.jwtdemo.model.user.AppUser;
import t1.openschool.jwtdemo.model.user.AppUserRole;
import t1.openschool.jwtdemo.model.user.EAppUserRole;
import t1.openschool.jwtdemo.repository.AppUserRepository;
import t1.openschool.jwtdemo.repository.AppUserRoleRepository;
import t1.openschool.jwtdemo.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final AppUserRepository appUserRepository;
    private final AppUserRoleRepository appUserRoleRepository;
    private final NewsRepository newsRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        createRoles();
        createDefaultUsers();
        createDefaultNews();
    }

    private void createRoles() {
        if (appUserRoleRepository.findByName(EAppUserRole.ROLE_USER).isEmpty()) {
            AppUserRole userRole = new AppUserRole();
            userRole.setName(EAppUserRole.ROLE_USER);
            appUserRoleRepository.save(userRole);
        }

        if (appUserRoleRepository.findByName(EAppUserRole.ROLE_AUTHOR).isEmpty()) {
            AppUserRole authorRole = new AppUserRole();
            authorRole.setName(EAppUserRole.ROLE_AUTHOR);
            appUserRoleRepository.save(authorRole);
        }

        if (appUserRoleRepository.findByName(EAppUserRole.ROLE_ADMIN).isEmpty()) {
            AppUserRole adminRole = new AppUserRole();
            adminRole.setName(EAppUserRole.ROLE_ADMIN);
            appUserRoleRepository.save(adminRole);
        }
    }

    private void createDefaultUsers() {
        if (appUserRepository.findByLogin("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setLogin("admin");
            admin.setPassword(passwordEncoder.encode("admin"));

            List<AppUserRole> roles = new ArrayList<>();
            roles.add(appUserRoleRepository.findByName(EAppUserRole.ROLE_USER).get());
            roles.add(appUserRoleRepository.findByName(EAppUserRole.ROLE_AUTHOR).get());
            roles.add(appUserRoleRepository.findByName(EAppUserRole.ROLE_ADMIN).get());
            admin.setRoles(roles);

            appUserRepository.save(admin);
        }

        if (appUserRepository.findByLogin("author").isEmpty()) {
            AppUser author = new AppUser();
            author.setLogin("author");
            author.setPassword(passwordEncoder.encode("author"));

            List<AppUserRole> roles = new ArrayList<>();
            roles.add(appUserRoleRepository.findByName(EAppUserRole.ROLE_USER).get());
            roles.add(appUserRoleRepository.findByName(EAppUserRole.ROLE_AUTHOR).get());
            author.setRoles(roles);

            appUserRepository.save(author);
        }

        if (appUserRepository.findByLogin("user").isEmpty()) {
            AppUser reader = new AppUser();
            reader.setLogin("user");
            reader.setPassword(passwordEncoder.encode("user"));

            List<AppUserRole> roles = new ArrayList<>();
            roles.add(appUserRoleRepository.findByName(EAppUserRole.ROLE_USER).get());
            reader.setRoles(roles);

            appUserRepository.save(reader);
        }
    }

    private void createDefaultNews() {
        if (!newsRepository.findAll().isEmpty()) {
            return;
        }

        AppUser author = appUserRepository.findByLogin("author").get();

        News news1 = new News();
        news1.setAuthor(author);
        news1.setHeader("News header 1");
        news1.setContent("News content 1");
        newsRepository.save(news1);

        News news2 = new News();
        news2.setAuthor(author);
        news2.setHeader("News header 2");
        news2.setContent("News content 2");
        newsRepository.save(news2);
    }
}
