package yong.app.domain.file.file;

import java.util.List;

public interface YongFileService {

    List<YongFileVO> list();

    Long join(YongFileDTO yongFileDTO);

    YongFileVO show(Long id);

    void update(Long id, YongFileDTO yongFileDTO);

    void delete(Long id);
}
