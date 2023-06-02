package yong.app.domain.region;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.user.YongUser;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class YongRegionDTO {

    private float latitude;
    private float langitude;
    @Enumerated(EnumType.STRING)
    private RegionCode regionCode;
}
