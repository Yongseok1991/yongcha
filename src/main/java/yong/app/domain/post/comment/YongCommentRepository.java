package yong.app.domain.post.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface YongCommentRepository extends JpaRepository<YongComment, Long> {
    @Query(
    """
    select new yong.app.domain.post.comment.YongCommentVO(
        y.id, 
        y.title, 
        y.content, 
        y.deleteYn
    ) 
    from YongComment y 
    where y.yongPost.id = :id
    """)
    List<YongCommentVO> findAllByYongPostId(@Param("id") Long id);
}

