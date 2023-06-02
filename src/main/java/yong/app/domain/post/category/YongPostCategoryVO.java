package yong.app.domain.post.category;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.post.post.YongPost;
import yong.app.domain.post.post.YongPostVO;

import java.util.List;

@Getter @Setter
public class YongPostCategoryVO {

    private Long id;
    private String name;
    private String description;
    private String deleteYn;
    private List<YongPostVO> posts;
}
