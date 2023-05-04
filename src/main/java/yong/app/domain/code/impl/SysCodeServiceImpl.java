package yong.app.domain.code.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.code.SysCode;
import yong.app.domain.code.SysCodeDTO;
import yong.app.domain.code.SysCodeRepository;
import yong.app.domain.code.SysCodeService;
import yong.app.global.response.StatusCode;
import yong.app.global.response.RestApiException;

/**
* @fileName SysCodeService
* @author dahyeon
* @version 1.0.0
* @date 2023-04-28
* @summary  sysCodeService : update, save 구현 
**/

@Service
@RequiredArgsConstructor
public class SysCodeServiceImpl implements SysCodeService {

    private final SysCodeRepository sysCodeRepository;

    @Transactional
    public SysCode save(SysCodeDTO sysCodeDTO){
        SysCode sysCode = new SysCodeDTO().toEntityForInsert(sysCodeDTO.getCode(), sysCodeDTO.getUpperCode(), sysCodeDTO.getCodeNm(), sysCodeDTO.getCodeDc(), sysCodeDTO.getUseAt(), sysCodeDTO.getLv(), sysCodeDTO.getRmDc(), sysCodeDTO.getSortNo());
        return sysCodeRepository.save(sysCode);
    }

    @Transactional
    public SysCode updateById(Long id, SysCodeDTO sysCodeDTO){
        SysCode sysCode = sysCodeRepository.findById(id).orElseThrow(() -> new RestApiException(StatusCode.BAD_REQUEST, "해당 code id가 없습니다."));
        sysCode.update(sysCodeDTO.getCode(), sysCodeDTO.getUpperCode(), sysCodeDTO.getCodeNm(), sysCodeDTO.getCodeDc(), sysCodeDTO.getUseAt(), sysCodeDTO.getLv(), sysCodeDTO.getRmDc(), sysCodeDTO.getSortNo());
        // dirty checking
        return sysCode;
    }
}
