package t1.openschool.jwtdemo.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.jwtdemo.dto.user.UserRequestPatchDto;
import t1.openschool.jwtdemo.dto.user.UserResponseFullDto;
import t1.openschool.jwtdemo.model.user.EAppUserRole;
import t1.openschool.jwtdemo.service.AppUserService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User admin controller test")
class UserAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Patch user with admin role should be 200 ok")
    public void testPatchUserWithAdminRole() throws Exception {
        long userId = 1L;
        UserRequestPatchDto userRequest = generateUserRequestPatchDto();
        UserResponseFullDto userResponse = UserResponseFullDto.builder().build();

        given(appUserService.patch(userId, userRequest)).willReturn(userResponse);

        mockMvc.perform(patch("/api/v1/admin/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "AUTHOR"})
    @DisplayName("Patch user with user or author role should be 401 unauthorized")
    @WithMockUser
    public void testPatchUserWithUserOrAuthorRole() throws Exception {
        long userId = 1L;
        UserRequestPatchDto userRequest = generateUserRequestPatchDto();

        mockMvc.perform(patch("/api/v1/admin/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Patch user without authentication should be 403 forbidden")
    public void testPatchUserWithoutAuthentication() throws Exception {
        long userId = 1L;
        UserRequestPatchDto userRequest = generateUserRequestPatchDto();

        mockMvc.perform(patch("/api/v1/admin/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete user with admin role should be 204 no content")
    public void testDeleteUserWithAdminRole() throws Exception {
        long userId = 1L;
        mockMvc.perform(delete("/api/v1/admin/user/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "AUTHOR"})
    @DisplayName("Delete user with user or author role should be 401 unauthorized")
    @WithMockUser
    public void testDeleteUserWithUserOrAuthorRole() throws Exception {
        long userId = 1L;
        mockMvc.perform(delete("/api/v1/admin/user/{id}", userId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Delete user without authentication should be 403 forbidden")
    public void testDeleteUserWithoutAuthentication() throws Exception {
        long userId = 1L;
        mockMvc.perform(delete("/api/v1/admin/user/{id}", userId))
                .andExpect(status().isForbidden());
    }

    private UserRequestPatchDto generateUserRequestPatchDto() {
        return UserRequestPatchDto.builder()
                .roles(Arrays.asList(EAppUserRole.ROLE_USER, EAppUserRole.ROLE_ADMIN))
                .build();
    }
}