package t1.openschool.jwtdemo.controller.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.jwtdemo.dto.user.UserResponseShortDto;
import t1.openschool.jwtdemo.service.AppUserService;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User private controller test")
class UserPrivateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Get all users with user role should be 200 ok")
    public void testGetAllUsersWithUserRole() throws Exception {
        given(appUserService.getAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/private/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get all users without authentication should be 403 forbidden")
    public void testGetAllUsersWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/private/user"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Get user by id with user role should be 200 ok")
    public void testGetUserByIdWithUserRole() throws Exception {
        long userId = 1L;
        given(appUserService.getById(userId)).willReturn(generateUserResponseShortDto());

        mockMvc.perform(get("/api/v1/private/user/{id}", userId))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get user by id without authentication should be 403 forbidden")
    public void testGetUserByIdWithoutAuthentication() throws Exception {
        long userId = 1L;
        mockMvc.perform(get("/api/v1/private/user/{id}", userId))
                .andExpect(status().isForbidden());
    }

    private UserResponseShortDto generateUserResponseShortDto() {
        return UserResponseShortDto.builder()
                .id(1L)
                .login("test user")
                .build();
    }
}