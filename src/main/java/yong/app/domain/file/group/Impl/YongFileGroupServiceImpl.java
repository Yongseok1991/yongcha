package yong.app.domain.file.group.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.group.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongFileGroupServiceImpl implements YongFileGroupService {

    private final YongFileGroupRepository yongFileGroupRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongFileGroupVO> list() {
        List<YongFileGroup> all = yongFileGroupRepository.findAll();
        return all.stream().map(yongFileGroup -> modelMapper.map(yongFileGroup, YongFileGroupVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongFileGroupDTO yongFileGroupDTO) {

        YongFileGroup yongFileGroup = YongFileGroup.insertFileGroupBuilder()
                .fileGroupName(yongFileGroupDTO.getFileGroupName())
                .description(yongFileGroupDTO.getDescription())
                .build();

        YongFileGroup save = yongFileGroupRepository.save(yongFileGroup);
        return save.getId();
    }

    @Override
    public YongFileGroupVO show(Long id) {
        YongFileGroup fileGroup = yongFileGroupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file group"));
        return modelMapper.map(fileGroup, YongFileGroupVO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongFileGroupDTO yongFileGroupDTO) {
        YongFileGroup fileGroup = yongFileGroupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file group"));
        fileGroup.updateFileGroup(yongFileGroupDTO);
    }
}
