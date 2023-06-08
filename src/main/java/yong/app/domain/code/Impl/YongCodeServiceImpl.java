package yong.app.domain.code.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.code.*;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class YongCodeServiceImpl implements YongCodeService {

    private final YongCodeRepository yongCodeRepository;

    @Override
    public List<YongCodeVO> list() {
        List<YongCode> all = yongCodeRepository.findAllByParentIsNull();
        return all.stream().map(YongCodeVO::of).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongCodeDTO yongCodeDTO) {

        YongCode yongCode = YongCode.insertYongCodeBuidler()
                .name(yongCodeDTO.getName())
                .description(yongCodeDTO.getDescription())
                .build();

        if(yongCodeDTO.getParentId() != null){
            YongCode parentCode = yongCodeRepository.findById(yongCodeDTO.getParentId())
                    .orElseThrow(() -> new NoSuchElementException("there is no parent code"));
            yongCode = yongCode.toBuilder().parent(parentCode).build();
        }

        YongCode save = yongCodeRepository.save(yongCode);
        return save.getId();
    }

    @Override
    public YongCodeVO show(Long id) {
        YongCode yongCode = yongCodeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no code"));
        return YongCodeVO.of(yongCode);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongCodeDTO yongCodeDTO) {

        YongCode yongCode = yongCodeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no code"));

        if(yongCodeDTO.getParentId() != null) {
            YongCode parentCode = yongCodeRepository.findById(yongCodeDTO.getParentId()).orElseThrow(() -> new NoSuchElementException("there is no parent code"));
            yongCodeDTO.setParentCode(parentCode);
        }
        yongCode.updateYongCode(yongCodeDTO);
    }
}
