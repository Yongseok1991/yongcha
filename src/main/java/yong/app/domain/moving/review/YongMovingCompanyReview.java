package yong.app.domain.moving.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.moving.company.YongMovingCompany;
import yong.app.global.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "yong_moving_company_review")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class YongMovingCompanyReview extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_moving_company_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_moving_company_id")
    private YongMovingCompany yongMovingCompany;

    private String content;

    private Long yongFileGroupId;

    @Transient
    private YongFileGroup yongFileGroup;

    @Column(name = "use_at")
    private String useAt;

    @Builder(builderMethodName = "insertReviewBuilder", toBuilder = true)
    public YongMovingCompanyReview(String content, YongMovingCompany yongMovingCompany, Long yongFileGroupId) {
        this.content = content;
        this.yongMovingCompany = yongMovingCompany;
        this.yongFileGroupId = yongFileGroupId;
        this.useAt = "Y";
    }

    public void updateReview(YongMovingCompanyReviewDTO reviewDTO, YongFileGroup yongFileGroup, YongMovingCompany yongMovingCompany){
        if(yongMovingCompany != null) this.yongMovingCompany = yongMovingCompany;
        if(reviewDTO.getContent() != null) this.content = reviewDTO.getContent();
        if(yongFileGroup != null) this.yongFileGroupId = yongFileGroup.getId();
    }

    public void removeReview(){this.useAt = "N";}
}
