package yong.app.domain.file.group;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.file.YongFileVO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongFileGroupVO {
    private String fileGroupName;
    private String description;
    private List<YongFileVO> files = new ArrayList<>();
}
