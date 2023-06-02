package yong.app.domain.post.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.domain.post.post.YongPost;
import yong.app.global.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "yong_comment")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongComment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_post_id")
    private YongPost yongPost;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "insertCommentBuilder")
    public YongComment(YongPost yongPost, String title, String content) {
        this.yongPost = yongPost;
        this.title = title;
        this.content = content;
        this.deleteYn = "N";
    }

    public void updateComment(YongCommentDTO yongCommentDTO){
        if(yongCommentDTO.getTitle() != null) this.title = yongCommentDTO.getTitle();
        if(yongCommentDTO.getContent() != null) this.content = yongCommentDTO.getContent();
    }

    public void deleteComment(){
        this.deleteYn = "Y";
    }
}
