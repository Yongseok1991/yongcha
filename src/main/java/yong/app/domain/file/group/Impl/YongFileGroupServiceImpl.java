package yong.app.domain.file.group.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.file.YongFileService;
import yong.app.domain.file.file.YongFileVO;
import yong.app.domain.file.group.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongFileGroupServiceImpl implements YongFileGroupService {

    private final YongFileGroupRepository yongFileGroupRepository;
    private final YongFileService yongFileService;

    @Override
    public List<YongFileGroupVO> list() {
        List<YongFileGroup> all = yongFileGroupRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("file group is empty");
        return all.stream().map(yongFileGroup -> new YongFileGroupVO(yongFileGroup, new ArrayList<>())).toList();
    }

    @Override
    public List<YongFileGroupVO> listWithFiles() {
        List<YongFileGroup> all = yongFileGroupRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("file group is empty");
        return all.stream().map(yongFileGroup -> {
            List<YongFileVO> files = yongFileService.findFilesByFileGroupId(yongFileGroup.getId());
            return new YongFileGroupVO(yongFileGroup, files);
        }).toList();
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
        return new YongFileGroupVO(fileGroup, new ArrayList<>());
    }

    @Override
    public YongFileGroupVO showWithFiles(Long id) {
        YongFileGroup fileGroup = yongFileGroupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file group"));
        List<YongFileVO> files = yongFileService.findFilesByFileGroupId(fileGroup.getId());
        return new YongFileGroupVO(fileGroup, files);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongFileGroupDTO yongFileGroupDTO) {
        YongFileGroup fileGroup = yongFileGroupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file group"));
        fileGroup.updateFileGroup(yongFileGroupDTO);
    }

    @Override
    public YongFileGroupVO findFileGroupWithFiles(Long fileGroupId) {
        YongFileGroup fileGroup = yongFileGroupRepository.findById(fileGroupId).orElseThrow(EntityNotFoundException::new);
        List<YongFileVO> files = yongFileService.findFilesByFileGroupId(fileGroup.getId());
        return new YongFileGroupVO(fileGroup, files);
    }
}
