package t1.openschool.jwtdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.jwtdemo.dto.security.AuthLoginRequestDto;
import t1.openschool.jwtdemo.dto.security.AuthRefreshRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Token integration test")
class TokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Login with correct login and pass should return access and refresh tokens")
    public void testLoginSuccess() throws Exception {
        AuthLoginRequestDto loginRequest =  AuthLoginRequestDto.builder()
                .login("admin")
                .password("admin")
                .build();

        mockMvc.perform(post("/api/v1/public/token/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    @DisplayName("Login with incorrect login and pass should be 401 unauthorized")
    public void testLoginFailure() throws Exception {
        AuthLoginRequestDto loginRequest =  AuthLoginRequestDto.builder()
                .login("admin")
                .password("wrong-password")
                .build();

        mockMvc.perform(post("/api/v1/public/token/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Refresh with correct refresh token should return access and refresh tokens")
    public void testRefreshTokenSuccess() throws Exception {
        AuthLoginRequestDto loginRequest =  AuthLoginRequestDto.builder()
                .login("admin")
                .password("admin")
                .build();

        String loginResponse = mockMvc.perform(post("/api/v1/public/token/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String refreshToken = objectMapper.readTree(loginResponse).get("refreshToken").asText();
        AuthRefreshRequestDto refreshRequest = AuthRefreshRequestDto.builder()
                .refreshToken(refreshToken)
                .build();

        mockMvc.perform(post("/api/v1/public/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    @DisplayName("Refresh with incorrect refresh token should be 401 unauthorized")
    public void testRefreshTokenFailure() throws Exception {
        AuthRefreshRequestDto refreshRequest = AuthRefreshRequestDto.builder()
                .refreshToken("invalid-refresh-token")
                .build();

        mockMvc.perform(post("/api/v1/public/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isUnauthorized());
    }
}