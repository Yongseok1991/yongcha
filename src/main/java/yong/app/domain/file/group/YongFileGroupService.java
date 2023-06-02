package yong.app.domain.file.group;

import java.util.List;

public interface YongFileGroupService {

    List<YongFileGroupVO> list();

    Long join(YongFileGroupDTO yongFileGroupDTO);

    YongFileGroupVO show(Long id);

    void update(Long id, YongFileGroupDTO yongFileGroupDTO);

}
