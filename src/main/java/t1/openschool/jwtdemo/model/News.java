package t1.openschool.jwtdemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import t1.openschool.jwtdemo.model.user.AppUser;

@Getter
@Setter
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Column(name = "news_header")
    private String header;

    @Column(name = "news_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "news_author_id")
    private AppUser author;
}
