package yong.app.domain.post.comment;

import java.util.List;

public interface YongCommentService {

    List<YongCommentVO> list();

    Long join(YongCommentDTO yongCommentDTO);

    YongCommentVO show(Long id);

    void update(Long id, YongCommentDTO yongCommentDTO);

    void delete(Long id);

    List<YongCommentVO> findAllCommentsByPostId(Long postId);
}
