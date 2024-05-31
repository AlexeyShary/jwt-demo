package t1.openschool.jwtdemo.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import t1.openschool.jwtdemo.dto.user.UserResponseShortDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response detailed news")
public class NewsResponseFullDto {
    private Long id;
    private String header;
    private String content;
    private UserResponseShortDto author;
}
