package yong.app.domain.post.post;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.YongFile;
import yong.app.domain.post.category.YongPostCategory;

@Getter @Setter
public class YongPostDTO {

    private Long id;
    private Long yongPostCategoryId; // insert용
    private String title;
    private String content;
    private Integer viewCount;
    private String deleteYn;
    private Long yongFileId; // insert용

    private YongPostCategory yongPostCategory;  // update용
    private YongFile yongFile; // update 용
}
