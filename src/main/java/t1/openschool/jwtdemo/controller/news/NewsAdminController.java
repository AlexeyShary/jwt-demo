package t1.openschool.jwtdemo.controller.news;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t1.openschool.jwtdemo.annotation.security.RequireAdminRole;
import t1.openschool.jwtdemo.dto.news.NewsRequestPatchDto;
import t1.openschool.jwtdemo.dto.news.NewsResponseFullDto;
import t1.openschool.jwtdemo.service.NewsService;

@RestController
@RequestMapping("/api/v1/admin/news")
@RequiredArgsConstructor
public class NewsAdminController {
    private final NewsService newsService;

    @RequireAdminRole
    @PatchMapping("/{id}")
    @Operation(summary = "Patch news")
    public NewsResponseFullDto patch(@PathVariable long id,
                                     @RequestBody NewsRequestPatchDto newsRequestPatchDto) {
        return newsService.patch(id, newsRequestPatchDto);
    }

    @RequireAdminRole
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete news")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        newsService.delete(id);
    }
}
