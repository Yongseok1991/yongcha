package yong.app.domain.post.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface YongPostRepository extends JpaRepository<YongPost, Long> {

    // post category와 fetch join을 통해 -> 전체 list 조회
//    @Query("select yp from YongPost yp left join fetch yp.postCategory")
    @Override
    @EntityGraph(attributePaths = {"postCategory"})
    List<YongPost> findAll();


    // post category와 fetch join을 통해 -> 1건 조회
//    @Query("select yp from YongPost yp left join fetch yp.postCategory where yp.id = :id")

    @EntityGraph(attributePaths = {"postCategory"})
    Optional<YongPost> findPostById(@Param("id") Long id);
}
