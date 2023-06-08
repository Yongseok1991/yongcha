package yong.app.domain.post.category;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class YongPostCategoryVO {

    private Long id;
    private String name;
    private String description;
    private String deleteYn;

    public YongPostCategoryVO(YongPostCategory yongPostCategory) {
        this.id = yongPostCategory.getId();
        this.name = yongPostCategory.getName();
        this.description = yongPostCategory.getDescription();
        this.deleteYn = yongPostCategory.getDeleteYn();
    }
}
