package yong.app.domain.code;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @fileName SysCodeService
* @author dahyeon
* @version 1.0.0
* @date 2023-04-28
* @summary  sysCodeService : update, save 구현 
**/
@Service
@RequiredArgsConstructor
public class SysCodeService {


    private final SysCodeRepository sysCodeRepository;

    @Transactional
    public SysCode save(SysCodeDTO sysCodeDTO){
        SysCode sysCode = new SysCodeDTO().toEntityForInsert(sysCodeDTO.getCode(), sysCodeDTO.getUpperCode(), sysCodeDTO.getCodeNm(), sysCodeDTO.getCodeDc(), sysCodeDTO.getUseAt(), sysCodeDTO.getLv(), sysCodeDTO.getRmDc(), sysCodeDTO.getSortNo());
        return sysCodeRepository.save(sysCode);
    }

    @Transactional
    public Long updateById(Long id, SysCodeDTO sysCodeDTO){

        SysCode sysCode = sysCodeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        sysCode.update(sysCode.getCode(), sysCodeDTO.getUpperCode(), sysCodeDTO.getCodeNm(), sysCodeDTO.getCodeDc(), sysCodeDTO.getUseAt(), sysCodeDTO.getLv(), sysCodeDTO.getRmDc(), sysCodeDTO.getSortNo());

        return id;
    }
}
