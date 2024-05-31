package t1.openschool.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.openschool.jwtdemo.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
