package yong.app.domain.post.comment;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class YongCommentDTO {

    private Long yongPostId;
    private String title;
    private String content;


}
