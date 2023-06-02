package yong.app.domain.moving.company;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.file.YongFileVO;

@Getter @Setter
public class YongMovingCompanyVO {

    private Long id;
    private String name;
    private String description;
    private YongFileVO yongFile;
}
