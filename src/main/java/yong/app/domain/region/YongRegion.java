package yong.app.domain.region;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.domain.user.YongUser;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "yong_region")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongRegion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_region_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_user_id")
    private YongUser yongUser;

    private float latitude;  // 위도
    private float langitude; // 경도

    @Enumerated(EnumType.STRING)
    private RegionCode regionCode;

    @Builder(builderMethodName = "insertYongRegionBuilder")
    public YongRegion(YongUser yongUser, float latitude, float langitude, RegionCode regionCode) {
        this.yongUser = yongUser;
        this.latitude = latitude;
        this.langitude = langitude;
        this.regionCode = regionCode;
    }

    public void updateYongRegion(YongRegionDTO yongRegionDTO){

        if(yongRegionDTO.getRegionCode() != null) this.regionCode = yongRegionDTO.getRegionCode();
        if(yongRegionDTO.getLatitude() != 0) this.latitude = yongRegionDTO.getLatitude();
        if(yongRegionDTO.getLangitude() != 0) this.langitude = yongRegionDTO.getLangitude();

    }
}
