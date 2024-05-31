package t1.openschool.jwtdemo.controller.news;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import t1.openschool.jwtdemo.annotation.security.RequireAuthorRole;
import t1.openschool.jwtdemo.annotation.security.RequireUserRole;
import t1.openschool.jwtdemo.dto.news.NewsRequestCreateDto;
import t1.openschool.jwtdemo.dto.news.NewsResponseFullDto;
import t1.openschool.jwtdemo.service.NewsService;

@RestController
@RequestMapping("/api/v1/private/news")
@RequiredArgsConstructor
public class NewsPrivateController {
    private final NewsService newsService;

    @RequireUserRole
    @GetMapping("/{id}")
    @Operation(summary = "Get news by id")
    public NewsResponseFullDto getById(@PathVariable long id) {
        return newsService.getById(id);
    }

    @RequireAuthorRole
    @PostMapping
    @Operation(summary = "Create new news")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseFullDto create(@RequestBody NewsRequestCreateDto newsRequestCreateDto,
                                      Authentication authentication) {
        return newsService.create(newsRequestCreateDto, authentication);
    }
}
