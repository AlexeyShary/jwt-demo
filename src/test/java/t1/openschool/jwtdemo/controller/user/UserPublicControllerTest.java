package t1.openschool.jwtdemo.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.jwtdemo.dto.user.UserRequestRegisterDto;
import t1.openschool.jwtdemo.dto.user.UserResponseFullDto;
import t1.openschool.jwtdemo.service.AppUserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User public controller test")
class UserPublicControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;

    @Test
    @DisplayName("Register new user without authentication should be 201 created")
    public void testRegisterUser() throws Exception {
        UserRequestRegisterDto userRequest = generateUserRequestRegisterDto();
        UserResponseFullDto userResponse = UserResponseFullDto.builder().build();

        given(appUserService.create(userRequest)).willReturn(userResponse);

        mockMvc.perform(post("/api/v1/public/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());
    }

    private UserRequestRegisterDto generateUserRequestRegisterDto() {
        return UserRequestRegisterDto.builder()
                .login("test_login")
                .password("test_password")
                .build();
    }
}