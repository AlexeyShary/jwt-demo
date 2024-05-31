package t1.openschool.jwtdemo.controller.news;

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
import t1.openschool.jwtdemo.dto.news.NewsRequestPatchDto;
import t1.openschool.jwtdemo.dto.news.NewsResponseFullDto;
import t1.openschool.jwtdemo.service.NewsService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("News admin controller test")
class NewsAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsService newsService;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Patch news with admin role should be 200 ok")
    public void testPatchNewsWithAdminRole() throws Exception {
        long newsId = 1L;
        NewsRequestPatchDto newsRequest = generateNewsRequestPatchDto();
        NewsResponseFullDto newsResponse = NewsResponseFullDto.builder().build();

        given(newsService.patch(newsId, newsRequest)).willReturn(newsResponse);

        mockMvc.perform(patch("/api/v1/admin/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequest)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "AUTHOR"})
    @DisplayName("Patch news with user or author role should be 401 unauthorized")
    @WithMockUser
    public void testPatchNewsWithUserOrAuthorRole() throws Exception {
        long newsId = 1L;
        NewsRequestPatchDto newsRequest = generateNewsRequestPatchDto();

        mockMvc.perform(patch("/api/v1/admin/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Patch news without authentication should be 403 forbidden")
    public void testPatchNewsWithoutAuthentication() throws Exception {
        long newsId = 1L;
        NewsRequestPatchDto newsRequest = generateNewsRequestPatchDto();

        mockMvc.perform(patch("/api/v1/admin/news/{id}", newsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete news with admin role should be 204 no content")
    public void testDeleteNewsWithAdminRole() throws Exception {
        long newsId = 1L;
        mockMvc.perform(delete("/api/v1/admin/news/{id}", newsId))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "AUTHOR"})
    @DisplayName("Delete news with user or author role should be 401 unauthorized")
    @WithMockUser
    public void testDeleteNewsWithUserOrAuthorRole() throws Exception {
        long newsId = 1L;
        mockMvc.perform(delete("/api/v1/admin/news/{id}", newsId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Delete news without authentication should be 403 forbidden")
    public void testDeleteNewsWithoutAuthentication() throws Exception {
        long newsId = 1L;
        mockMvc.perform(delete("/api/v1/admin/news/{id}", newsId))
                .andExpect(status().isForbidden());
    }

    private NewsRequestPatchDto generateNewsRequestPatchDto() {
        return NewsRequestPatchDto.builder()
                .header("Test news header")
                .content("Test news content")
                .build();
    }
}