package yong.app.domain.file.group.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.file.YongFileService;
import yong.app.domain.file.file.YongFileVO;
import yong.app.domain.file.group.*;

import javax.persistence.EntityNotFoundException;
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
        return all.stream().map(YongFileGroupVO::new).toList();
    }

    @Override
    public List<YongFileGroupVO> listWithFiles() {
        List<YongFileGroupVO> all = yongFileGroupRepository.findAllFileGroup();
        all.forEach(yongFileGroup -> {
            List<YongFileVO> files = yongFileService.findFilesByFileGroupId(yongFileGroup.getId());
            yongFileGroup.setFiles(files);
        });
        return all;
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
        return new YongFileGroupVO(fileGroup);
    }

    @Override
    public YongFileGroupVO showWithFiles(Long id) {
        YongFileGroup fileGroup = yongFileGroupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file group"));
        List<YongFileVO> files = yongFileService.findFilesByFileGroupId(fileGroup.getId());
        YongFileGroupVO yongFileGroupVO = new YongFileGroupVO(fileGroup);
        yongFileGroupVO.setFiles(files);
        return yongFileGroupVO;
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongFileGroupDTO yongFileGroupDTO) {
        YongFileGroup fileGroup = yongFileGroupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file group"));
        fileGroup.updateFileGroup(yongFileGroupDTO);
    }

    @Override
    public YongFileGroupVO findPostFileGroup(Long fileGroupId) {
        return yongFileGroupRepository.findPostFileGroupById(fileGroupId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
