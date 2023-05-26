package yong.app.domain.post.post;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.post.category.YongPostCategory;

@Getter @Setter
public class YongPostVO {

    private Long id;
    private YongPostCategory yongPostCategory;
    private String title;
    private String content;
    private String deleteYn;
    private Integer viewCount;
}
