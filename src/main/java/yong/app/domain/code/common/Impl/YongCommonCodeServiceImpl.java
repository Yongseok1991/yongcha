package yong.app.domain.code.common.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.code.common.*;
import yong.app.domain.code.group.YongGroupCode;
import yong.app.domain.code.group.YongGroupCodeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongCommonCodeServiceImpl implements YongCommonCodeService{

    private final YongCommonCodeRepository yongCommonCodeRepository;
    private final YongGroupCodeRepository yongGroupCodeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongCommonCodeVO> list() {
        List<YongCommonCode> all = yongCommonCodeRepository.findAll();
        return all.stream().map(yongCommonCode -> modelMapper.map(yongCommonCode, YongCommonCodeVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongCommonCodeDTO yongCommonCodeDTO) {

        if(yongCommonCodeDTO.getYongGroupCodeId() == null) throw new NullPointerException("group code is null");

        YongGroupCode parentGroupCode = yongGroupCodeRepository.findByIdAndDeleteYnIs(yongCommonCodeDTO.getYongGroupCodeId(), "N")
                .orElseThrow(() -> new NoSuchElementException("there is no group code"));

        YongCommonCode yongCommonCode = YongCommonCode.insertCommonCodeBuilder()
                .yongGroupCode(parentGroupCode)
                .name(yongCommonCodeDTO.getName())
                .description(yongCommonCodeDTO.getDescription())
                .build();

        YongCommonCode save = yongCommonCodeRepository.save(yongCommonCode);

        return save.getId();
    }

    @Override
    public YongCommonCodeVO show(Long id) {
        YongCommonCode yongCommonCode = yongCommonCodeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no common code"));
        return modelMapper.map(yongCommonCode, YongCommonCodeVO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongCommonCodeDTO yongCommonCodeDTO) {

        if(yongCommonCodeDTO.getYongGroupCodeId() == null) throw new NullPointerException("group code is null");

        YongGroupCode parentGroupCode = yongGroupCodeRepository.findByIdAndDeleteYnIs(yongCommonCodeDTO.getYongGroupCodeId(), "N")
                .orElseThrow(() -> new NoSuchElementException("there is no group code"));
        yongCommonCodeDTO.setYongGroupCode(parentGroupCode);

        YongCommonCode yongCommonCode = yongCommonCodeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no common code"));
        yongCommonCode.updateCommonCode(yongCommonCodeDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongCommonCode yongCommonCode = yongCommonCodeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no common code"));
        yongCommonCode.deleteCommonCode();
    }
}
