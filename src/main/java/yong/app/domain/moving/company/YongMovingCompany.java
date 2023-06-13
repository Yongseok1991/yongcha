package yong.app.domain.moving.company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.domain.base.Address;
import yong.app.domain.file.file.YongFile;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.moving.review.YongMovingCompanyReview;
import yong.app.domain.post.comment.YongComment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "yong_moving_company")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongMovingCompany {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_moving_company_id")
    private Long id;

    private String name;
    private String description;

    @Column(name = "person_count") // 직원수
    private Integer personCount;

    private Integer history; // 경력 년수

    @Column(name = "use_count") // 고용횟수
    private Integer useCount;

    private Long yongFileGroupId;

    @Transient
    private YongFileGroup yongFileGroup;

    private float latitude;
    private float langitude;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "ceo_name")
    private String ceoName;

    @Column(name = "business_number")
    private String businessNumber;

    @Embedded
    private Address address;

    @Column(name = "contact_time")
    private String contactTime;

    @Column(name = "delete_yn")
    private String deleteYn;

    @OneToMany(mappedBy = "yongMovingCompany")
    private List<YongMovingCompanyReview> reviews = new ArrayList<>();

    @Builder(builderMethodName = "insertMovingCompanyBuilder", toBuilder = true)
    public YongMovingCompany(String name, Long yongFileGroupId, String description) {
        this.name = name;
        this.yongFileGroupId = yongFileGroupId;
        this.description = description;
        this.deleteYn = "N";
    }

    public void updateMovingCompany(YongMovingCompanyDTO movingDTO){
        if(movingDTO.getName() != null) this.name = movingDTO.getName();
        if(movingDTO.getDescription() != null) this.description = movingDTO.getDescription();
        if(movingDTO.getYongFileGroupId() != null) this.yongFileGroupId = movingDTO.getYongFileGroupId();
    }

    public void deleteMovingCompany(){
        this.deleteYn = "Y";
    }
}
