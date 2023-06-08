package yong.app.domain.post.category;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yong.app.domain.post.post.YongPost;
import yong.app.domain.post.post.YongPostRepository;

import java.util.List;
import java.util.Optional;

public interface YongPostCategoryRepository extends JpaRepository<YongPostCategory, Long> {

    Optional<YongPostCategory> findByIdAndDeleteYnIs(Long id, String deleteYn);
}
