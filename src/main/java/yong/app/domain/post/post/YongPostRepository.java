package yong.app.domain.post.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface YongPostRepository extends JpaRepository<YongPost, Long> {

    @Override
    List<YongPost> findAll();





    @EntityGraph(attributePaths = {"postCategory", "yongFile"})
    Optional<YongPost> findPostById(@Param("id") Long id);

    Optional<YongPost> findByIdAndDeleteYnIs(Long id, String deleteYn);
}
