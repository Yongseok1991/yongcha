package yong.app.domain.code.common;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.code.group.YongGroupCodeVO;

@Getter @Setter
public class YongCommonCodeVO {

    private Long id;
    private YongGroupCodeVO yongGroupCode;
    private String name;
    private String description;
    private String deleteYn;
}
