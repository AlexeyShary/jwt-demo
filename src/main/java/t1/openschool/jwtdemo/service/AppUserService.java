package t1.openschool.jwtdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t1.openschool.jwtdemo.dto.user.UserRequestPatchDto;
import t1.openschool.jwtdemo.dto.user.UserRequestRegisterDto;
import t1.openschool.jwtdemo.dto.user.UserResponseFullDto;
import t1.openschool.jwtdemo.dto.user.UserResponseShortDto;
import t1.openschool.jwtdemo.model.user.AppUser;
import t1.openschool.jwtdemo.model.user.AppUserRole;
import t1.openschool.jwtdemo.model.user.EAppUserRole;
import t1.openschool.jwtdemo.repository.AppUserRepository;
import t1.openschool.jwtdemo.repository.AppUserRoleRepository;
import t1.openschool.jwtdemo.util.exceptions.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AppUserRoleRepository appUserRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponseShortDto> getAll() {
        return appUserRepository.findAll().stream()
                .map(this::toShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseShortDto getById(long id) {
        return toShortDto(findAppUserById(id));
    }

    @Transactional
    public UserResponseFullDto create(UserRequestRegisterDto dto) {
        AppUser appUser = new AppUser();

        AppUserRole addCommentsRole = appUserRoleRepository.findByName(EAppUserRole.ROLE_USER)
                .orElseThrow(() -> new NotFoundException("Role not found: " + EAppUserRole.ROLE_USER));

        appUser.setLogin(dto.getLogin());
        appUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        appUser.setRoles(Collections.singletonList(addCommentsRole));

        return toFullDto(appUserRepository.save(appUser));
    }

    @Transactional
    public UserResponseFullDto patch(long id, UserRequestPatchDto dto) {
        AppUser appUser = findAppUserById(id);

        appUser.setRoles(dto.getRoles().stream()
                .map(this::findAppUserRoleByName)
                .collect(Collectors.toList()));

        return toFullDto(appUserRepository.save(appUser));
    }

    @Transactional
    public void delete(long id) {
        appUserRepository.deleteById(id);
    }

    public AppUser findAppUserById(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id=" + id + " not found"));
    }

    public AppUser findAppUserByLogin(String login) {
        return appUserRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User not found: " + login));
    }

    private AppUserRole findAppUserRoleByName(EAppUserRole roleName) {
        return appUserRoleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role " + roleName + " not found"));
    }

    private UserResponseFullDto toFullDto(AppUser entity) {
        return UserResponseFullDto.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .roles(entity.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                .build();
    }

    private UserResponseShortDto toShortDto(AppUser entity) {
        return UserResponseShortDto.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .roles(entity.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                .build();
    }
}