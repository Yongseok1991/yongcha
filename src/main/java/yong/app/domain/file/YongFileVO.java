package yong.app.domain.file;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class YongFileVO {

    private Long id;
    private String fileName;
    private String description;
    private YongFile parent;
    private List<YongFile> yongFiles;

}
