package yong.app.domain.code.common;

import java.util.List;

public interface YongCommonCodeService {

    List<YongCommonCodeVO> list();

    Long join(YongCommonCodeDTO yongCommonCodeDTO);

    YongCommonCodeVO show(Long id);

    void update(Long id, YongCommonCodeDTO yongCommonCodeDTO);

    void delete(Long id);

}
