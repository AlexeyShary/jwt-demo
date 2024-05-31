package t1.openschool.jwtdemo.controller.news;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.openschool.jwtdemo.dto.news.NewsResponseShortDto;
import t1.openschool.jwtdemo.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/news")
@RequiredArgsConstructor
public class NewsPublicController {
    private final NewsService newsService;

    @GetMapping
    @Operation(summary = "Get all news")
    public List<NewsResponseShortDto> getAll() {
        return newsService.getAll();
    }
}
