package yong.app.domain.moving.company;

import java.util.List;

public interface YongMovingCompanyService {
    List<YongMovingCompanyVO> list();

    Long join(YongMovingCompanyDTO yongMovingCompanyDTO);

    YongMovingCompanyVO show(Long id);

    void update(Long id, YongMovingCompanyDTO yongMovingCompanyDTO);

    void delete(Long id);
}
