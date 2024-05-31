package t1.openschool.jwtdemo.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Author request to create news")
public class NewsRequestCreateDto {
    @NotBlank
    private String header;

    @NotBlank
    private String content;
}