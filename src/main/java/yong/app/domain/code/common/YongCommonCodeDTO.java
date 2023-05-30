package yong.app.domain.code.common;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.code.group.YongGroupCode;

@Getter @Setter
public class YongCommonCodeDTO {

    private String name;
    private String description;
    private Long yongGroupCodeId;

    private YongGroupCode yongGroupCode;
}
