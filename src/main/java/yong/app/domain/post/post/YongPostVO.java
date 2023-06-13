package yong.app.domain.post.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.file.YongFile;
import yong.app.domain.file.file.YongFileVO;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.file.group.YongFileGroupVO;
import yong.app.domain.post.comment.YongComment;
import yong.app.domain.post.comment.YongCommentVO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongPostVO {

    private Long id;
    private String title;
    private String content;
    private String deleteYn;
    private Integer viewCount;
    private List<YongCommentVO> comments = new ArrayList<>();
    private YongFileGroupVO yongFileGroup;

    @QueryProjection
    public YongPostVO(Long id, String title, String content, List<YongCommentVO> comments, String deleteYn, Integer viewCount, YongFileGroupVO yongFileGroup){
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.deleteYn = deleteYn;
        this.viewCount = viewCount;
        this.yongFileGroup = yongFileGroup;
    }

    public YongPostVO(YongPost yongPost, List<YongCommentVO> comments,  YongFileGroupVO yongFileGroup){
        this.id = yongPost.getId();
        this.title = yongPost.getTitle();
        this.content = yongPost.getContent();
        this.deleteYn = yongPost.getDeleteYn();
        this.viewCount = yongPost.getViewCount();
        this.comments = comments;
        this.yongFileGroup = yongFileGroup;
    }
}
