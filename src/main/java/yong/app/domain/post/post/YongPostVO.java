package yong.app.domain.post.post;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.file.YongFileVO;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.file.group.YongFileGroupVO;
import yong.app.domain.post.comment.YongCommentVO;

import java.util.List;

@Getter @Setter
public class YongPostVO {

    private Long id;
    private String title;
    private String content;
    private String deleteYn;
    private Integer viewCount;

    private YongFileGroupVO yongFileGroup;
    private List<YongCommentVO> comments;
}
