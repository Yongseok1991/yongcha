package yong.app.domain.post.post;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.file.YongFile;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.post.category.YongPostCategory;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongPostDTO {

    private Long id;
    private Long yongPostCategoryId; // insert용
    private String title;
    private String content;
    private Integer viewCount;
    private String deleteYn;
    private Long yongFileGroupId; // insert용

    private List<String> addFiles = new ArrayList<>();
    private List<Long> delFiles = new ArrayList<>();


    private YongPostCategory yongPostCategory;  // update용
    private YongFileGroup yongFileGroup;                  // update 용
}
