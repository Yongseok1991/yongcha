package yong.app.domain.code.group.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.code.common.YongCommonCode;
import yong.app.domain.code.common.YongCommonCodeRepository;
import yong.app.domain.code.group.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class YongGroupCodeServiceImpl implements YongGroupCodeService {

    private final YongCommonCodeRepository yongCommonCodeRepository;
    private final YongGroupCodeRepository yongGroupCodeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongGroupCodeVO> list() {
        List<YongGroupCode> all = yongGroupCodeRepository.findAll();
        return all.stream().map(yongGroupCode -> modelMapper.map(yongGroupCode, YongGroupCodeVO.class)).collect(Collectors.toList());
    }

    @Override
    public Long join(YongGroupCodeDTO yongGroupCodeDTO) {

        YongGroupCode yongGroupCode = YongGroupCode.insertGroupCodeBuilder()
                .name(yongGroupCodeDTO.getName())
                .description(yongGroupCodeDTO.getDescription())
                .build();

        YongGroupCode save = yongGroupCodeRepository.save(yongGroupCode);
        return save.getId();
    }

    @Override
    public YongGroupCodeVO show(Long id) {
        YongGroupCode yongGroupCode = yongGroupCodeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no group code"));
        return modelMapper.map(yongGroupCode, YongGroupCodeVO.class);
    }

    @Override
    public void update(Long id, YongGroupCodeDTO yongGroupCodeDTO) {
        YongGroupCode yongGroupCode = yongGroupCodeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no group code"));
        yongGroupCode.updateGroupCode(yongGroupCodeDTO);
    }

    @Override
    public void delete(Long id) {
        YongGroupCode yongGroupCode = yongGroupCodeRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("there is no group code"));
        List<YongCommonCode> findChildCommonCode = yongCommonCodeRepository.findAllByYongGroupCode(yongGroupCode);

        if(!findChildCommonCode.isEmpty()){
            throw new IllegalStateException("there is common code .. you can't delete it");
        }
        yongGroupCode.delteGroupCode();
    }
}
