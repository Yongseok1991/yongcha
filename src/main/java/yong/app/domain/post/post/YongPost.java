package yong.app.domain.post.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yong.app.domain.file.YongFile;
import yong.app.domain.post.category.YongPostCategory;
import yong.app.global.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "yong_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YongPost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_post_category_id")
    private YongPostCategory postCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_file_id")
    private YongFile yongFile;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "delete_yn")
    private String deleteYn;


    // insert 용
    @Builder(builderMethodName = "insertPostBuilder", toBuilder = true)
    public YongPost(YongPostCategory postCategory, String title, String content, Integer viewCount, YongFile yongFile) {
        this.postCategory = postCategory;
        this.yongFile = yongFile;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.deleteYn = "N";
    }


    // update 용
    public void updatePost(YongPostDTO yongPostDTO){
        if(yongPostDTO.getYongPostCategory() != null) this.postCategory = yongPostDTO.getYongPostCategory();
        if(yongPostDTO.getYongFile() != null) this.yongFile = yongPostDTO.getYongFile();
        if(yongPostDTO.getTitle() != null) this.title = yongPostDTO.getTitle();
        if(yongPostDTO.getContent() != null) this.content = yongPostDTO.getContent();
        if(yongPostDTO.getViewCount() != null) this.viewCount = yongPostDTO.getViewCount();
    }

    // delete 용
    public void deletePost(){
        this.deleteYn = "Y";
    }
}
