package yong.app.domain.file.group;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.file.YongFileVO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongFileGroupVO {
    private Long id;
    private String fileGroupName;
    private String description;
    private List<YongFileVO> files = new ArrayList<>();

    // findPostFileGroupById
    @QueryProjection
    public YongFileGroupVO(Long id, String fileGroupName, String description) {
        this.id = id;
        this.fileGroupName = fileGroupName;
        this.description = description;
    }

    public YongFileGroupVO(YongFileGroup yongFileGroup) {
        this.id = yongFileGroup.getId();
        this.fileGroupName = yongFileGroup.getFileGroupName();
        this.description = yongFileGroup.getDescription();
    }
}
