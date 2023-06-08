package yong.app.domain.code;

import java.util.List;

public interface YongCodeService {

     List<YongCodeVO> list();

     Long join(YongCodeDTO yongCodeDTO);

     YongCodeVO show(Long id);

     void update(Long id, YongCodeDTO yongCodeDTO);

}
