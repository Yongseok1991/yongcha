package yong.app.domain.file;

import java.util.List;

public interface YongFileService {

    List<YongFileVO> findAll();

    Long insert(YongFileDTO yongFileDTO);
}
