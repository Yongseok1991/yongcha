package yong.app.domain.post.comment;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.post.post.YongPostVO;

@Getter @Setter
public class YongCommentVO {

    private Long id;
    private String title;
    private String content;
    private String deleteYn;
    private YongPostVO yongPost;
}
