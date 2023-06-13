package yong.app.domain.moving.review;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.group.YongFileGroupVO;

@Getter @Setter
public class YongMovingCompanyReviewVO {
    private Long id;
    private String content;
    private String useAt;
    private YongFileGroupVO yongFileGroupVO;

    public YongMovingCompanyReviewVO(YongMovingCompanyReview yongMovingCompanyReview, YongFileGroupVO yongFileGroupVO) {
        this.id = yongMovingCompanyReview.getId();
        this.content = yongMovingCompanyReview.getContent();
        this.useAt = yongMovingCompanyReview.getUseAt();
        this.yongFileGroupVO = yongFileGroupVO;
    }
}
