package yong.app.domain.region;

import java.util.List;

public interface YongRegionService {

    List<YongRegionVO> list();

    Long joinByLoginEmail(String email, YongRegionDTO yongRegionDTO);

    List<YongRegionVO> showByLoginEmail(String email);

    void updateByLoginEmail(Long id, String email, YongRegionDTO yongRegionDTO);

    void deleteByLoginEmail(Long id, String email);
}
