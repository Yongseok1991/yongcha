package yong.app.domain.post.post;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.YongFileVO;
import yong.app.domain.post.category.YongPostCategoryVO;

@Getter @Setter
public class YongPostVO {

    private Long id;
    private YongPostCategoryVO yongPostCategory;
    private String title;
    private String content;
    private String deleteYn;
    private Integer viewCount;

    private YongFileVO yongFile;
}
