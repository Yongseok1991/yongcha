package yong.app.domain.code.group;

import java.util.List;

public interface YongGroupCodeService {

    List<YongGroupCodeVO> list();

    Long join(YongGroupCodeDTO yongGroupCodeDTO);

    YongGroupCodeVO show(Long id);

    void update(Long id, YongGroupCodeDTO yongGroupCodeDTO);

    void delete(Long id);
}
