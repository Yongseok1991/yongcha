package yong.app.domain.code;

import org.springframework.transaction.annotation.Transactional;
import yong.app.global.response.RestApiException;
import yong.app.global.response.StatusCode;

public interface SysCodeService {

    SysCode save(SysCodeDTO sysCodeDTO);
    SysCode updateById(Long id, SysCodeDTO sysCodeDTO);
}
