package t1.openschool.jwtdemo.controller.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.jwtdemo.dto.news.NewsRequestCreateDto;
import t1.openschool.jwtdemo.dto.news.NewsResponseFullDto;
import t1.openschool.jwtdemo.service.NewsService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("News private controller test")
public class NewsPrivateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsService newsService;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Get news by id with user role should be 200 ok")
    public void testGetNewsByIdWithUserRole() throws Exception {
        long newsId = 1L;
        NewsResponseFullDto newsResponse = new NewsResponseFullDto();
        newsResponse.setId(newsId);

        given(newsService.getById(newsId)).willReturn(newsResponse);

        mockMvc.perform(get("/api/v1/private/news/{id}", newsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newsId));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Get news by id without authentication should be 403 forbidden")
    public void testGetNewsByIdWithoutAuthentication() throws Exception {
        long newsId = 1L;
        mockMvc.perform(get("/api/v1/private/news/{id}", newsId))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    @DisplayName("Create news with author role should be 201 created")
    public void testCreateNewsWithAuthorRole() throws Exception {
        NewsRequestCreateDto newsRequest = generateNewsRequestCreateDto();
        NewsResponseFullDto newsResponse = NewsResponseFullDto.builder().build();

        given(newsService.create(newsRequest, null)).willReturn(newsResponse);

        mockMvc.perform(post("/api/v1/private/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Create news with user role should be 401 unauthorized")
    public void testCreateNewsWithUserRole() throws Exception {
        NewsRequestCreateDto newsRequest = generateNewsRequestCreateDto();

        mockMvc.perform(post("/api/v1/private/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Create news without authentication should be 403 forbidden")
    public void testCreateNewsWithoutAuthentication() throws Exception {
        NewsRequestCreateDto newsRequest = generateNewsRequestCreateDto();

        mockMvc.perform(post("/api/v1/private/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequest)))
                .andExpect(status().isForbidden());
    }

    private NewsRequestCreateDto generateNewsRequestCreateDto() {
        return NewsRequestCreateDto.builder()
                .header("Test news header")
                .content("Test news content")
                .build();
    }
}