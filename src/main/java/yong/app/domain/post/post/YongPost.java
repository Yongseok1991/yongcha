package yong.app.domain.post.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import yong.app.domain.file.file.YongFile;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.post.category.YongPostCategory;
import yong.app.domain.post.comment.YongComment;
import yong.app.domain.post.comment.YongCommentVO;
import yong.app.global.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "yong_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class YongPost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_post_category_id")
    @JsonIgnore
    private YongPostCategory postCategory;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "yong_file_id")
//    @JsonIgnore
    @Transient
    private YongFileGroup yongFileGroup;

    private Long yongFileGroupId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "delete_yn")
    private String deleteYn;

    @OneToMany(mappedBy = "yongPost")
    private List<YongComment> comments = new ArrayList<>();


    // insert 용
    @Builder(builderMethodName = "insertPostBuilder", toBuilder = true)
    public YongPost(YongPostCategory postCategory, String title, String content, Integer viewCount, Long yongFileGroupId) {
        this.postCategory = postCategory;
        this.yongFileGroupId = yongFileGroupId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.deleteYn = "N";
    }
    // update 용
    public void updatePost(YongPostDTO yongPostDTO){
        if(yongPostDTO.getYongPostCategory() != null) this.postCategory = yongPostDTO.getYongPostCategory();
        if(yongPostDTO.getYongFileGroup() != null) this.yongFileGroupId = yongPostDTO.getYongFileGroupId();
        if(yongPostDTO.getTitle() != null) this.title = yongPostDTO.getTitle();
        if(yongPostDTO.getContent() != null) this.content = yongPostDTO.getContent();
        if(yongPostDTO.getViewCount() != null) this.viewCount = yongPostDTO.getViewCount();
    }

    // delete 용
    public void deletePost(){
        this.deleteYn = "Y";
    }
}
