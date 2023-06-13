package yong.app.domain.post.post;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongPostDTO {

    private Long yongPostCategoryId; // insert용
    private String title;
    private String content;
    private Integer viewCount;
    private Long yongFileGroupId; // update

    private List<String> addFiles = new ArrayList<>();
    private List<Long> delFiles = new ArrayList<>();

}
