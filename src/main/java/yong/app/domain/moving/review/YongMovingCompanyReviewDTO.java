package yong.app.domain.moving.review;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.moving.company.YongMovingCompany;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongMovingCompanyReviewDTO {

    private Long yongMovingCompanyId;
    private Long yongFileGroupId;
    private String content;
    private List<String> addFiles = new ArrayList<>();
    private List<Long> delFiles = new ArrayList<>();
}
