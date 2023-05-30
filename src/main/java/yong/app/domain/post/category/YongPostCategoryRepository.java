package yong.app.domain.post.category;

import org.springframework.data.jpa.repository.JpaRepository;
import yong.app.domain.post.post.YongPost;

import java.util.Optional;

public interface YongPostCategoryRepository extends JpaRepository<YongPostCategory, Long> {

    Optional<YongPostCategory> findByIdAndDeleteYnIs(Long id, String deleteYn);
}
