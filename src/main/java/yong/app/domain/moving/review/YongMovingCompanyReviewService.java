package yong.app.domain.moving.review;

import java.util.List;

public interface YongMovingCompanyReviewService {

    List<YongMovingCompanyReviewVO> list();

    List<YongMovingCompanyReviewVO> listWithFiles();

    Long join(YongMovingCompanyReviewDTO yongMovingCompanyReviewDTO);

    YongMovingCompanyReviewVO show(Long id);

    YongMovingCompanyReviewVO showWithFiles(Long id);

    void update(Long id, YongMovingCompanyReviewDTO yongMovingCompanyReviewDTO);

    void delete(Long id);
}
