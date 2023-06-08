package yong.app.domain.post.comment;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import yong.app.domain.post.post.YongPostVO;

@Getter @Setter
@Builder
public class YongCommentVO {
    private Long id;
    private String title;
    private String content;
    private String deleteYn;

    @QueryProjection
    public YongCommentVO(Long id, String title, String content, String deleteYn) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.deleteYn = deleteYn;
    }


    public YongCommentVO(YongComment yongComment) {
        this.id = yongComment.getId();
        this.title = yongComment.getTitle();
        this.content = yongComment.getContent();
        this.deleteYn = yongComment.getDeleteYn();
   }
//
//    public YongCommentVO(String title, String content, String deleteYn) {
//        this.title = title;
//        this.content = content;
//        this.deleteYn = deleteYn;
//    }
}
