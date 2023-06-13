package yong.app.domain.moving.company;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.group.YongFileGroupVO;

@Getter @Setter
public class YongMovingCompanyVO {

    private Long id;
    private String name;
    private String description;
    private YongFileGroupVO yongFileGroup;

    public YongMovingCompanyVO(YongMovingCompany yongMovingCompany, YongFileGroupVO yongFileGroup) {
        this.id = yongMovingCompany.getId();
        this.name = yongMovingCompany.getName();
        this.description = yongMovingCompany.getDescription();
        this.yongFileGroup = yongFileGroup;
    }
}
