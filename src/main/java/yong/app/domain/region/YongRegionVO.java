package yong.app.domain.region;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserVO;

@Getter @Setter
public class YongRegionVO {

    private Long id;
    private YongUserVO yongUser;
    private float latitude;
    private float langitude;
    private RegionCode regionCode;
}
