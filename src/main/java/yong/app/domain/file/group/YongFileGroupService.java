package yong.app.domain.file.group;

import java.util.List;

public interface YongFileGroupService {

    List<YongFileGroupVO> list();

    List<YongFileGroupVO> listWithFiles();

    Long join(YongFileGroupDTO yongFileGroupDTO);

    YongFileGroupVO show(Long id);

    YongFileGroupVO showWithFiles(Long id);

    void update(Long id, YongFileGroupDTO yongFileGroupDTO);

    YongFileGroupVO findFileGroupWithFiles(Long fileGroupId);
}
