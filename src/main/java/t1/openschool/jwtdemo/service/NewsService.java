package t1.openschool.jwtdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t1.openschool.jwtdemo.dto.news.NewsRequestCreateDto;
import t1.openschool.jwtdemo.dto.news.NewsRequestPatchDto;
import t1.openschool.jwtdemo.dto.news.NewsResponseFullDto;
import t1.openschool.jwtdemo.dto.news.NewsResponseShortDto;
import t1.openschool.jwtdemo.dto.user.UserResponseShortDto;
import t1.openschool.jwtdemo.model.News;
import t1.openschool.jwtdemo.repository.NewsRepository;
import t1.openschool.jwtdemo.util.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final AppUserService appUserService;
    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public List<NewsResponseShortDto> getAll() {
        return newsRepository.findAll().stream()
                .map(this::toShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NewsResponseFullDto getById(long id) {
        return toFullDto(findNewsById(id));
    }

    @Transactional
    public NewsResponseFullDto create(NewsRequestCreateDto dto, Authentication authentication) {
        News news = new News();

        news.setAuthor(appUserService.findAppUserByLogin(authentication.getName()));
        news.setHeader(dto.getHeader());
        news.setContent(dto.getContent());

        return toFullDto(newsRepository.save(news));
    }

    @Transactional
    public NewsResponseFullDto patch(long id, NewsRequestPatchDto dto) {
        News news = findNewsById(id);

        news.setHeader(dto.getHeader());
        news.setContent(dto.getContent());

        return toFullDto(newsRepository.save(news));
    }

    @Transactional
    public void delete(long id) {
        newsRepository.deleteById(id);
    }

    private News findNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("News id=" + id + " not found"));
    }

    private NewsResponseFullDto toFullDto(News entity) {
        return NewsResponseFullDto.builder()
                .id(entity.getId())
                .header(entity.getHeader())
                .content(entity.getContent())
                .author(UserResponseShortDto.builder()
                        .id(entity.getAuthor().getId())
                        .login(entity.getAuthor().getLogin())
                        .roles(entity.getAuthor().getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                        .build())
                .build();
    }

    private NewsResponseShortDto toShortDto(News entity) {
        return NewsResponseShortDto.builder()
                .header(entity.getHeader())
                .build();
    }
}
