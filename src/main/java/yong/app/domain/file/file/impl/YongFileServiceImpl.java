package yong.app.domain.file.file.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.file.*;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.file.group.YongFileGroupRepository;
import yong.app.domain.notification.type.YongNotificationType;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongFileServiceImpl implements YongFileService {

    private final YongFileRepository yongFileRepository;
    private final YongFileGroupRepository fileGroupRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongFileVO> list() {
        List<YongFile> findAll = yongFileRepository.findAll();
        return findAll.stream().map(yongFile -> modelMapper.map(yongFile, YongFileVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongFileDTO yongFileDTO) {

        if(yongFileDTO.getYongFileGroupId() == null) throw new NullPointerException("file group id is null");

        YongFileGroup fileGroup = fileGroupRepository.findById(yongFileDTO.getYongFileGroupId()).orElseThrow(() -> new NoSuchElementException("there is no file group"));

        // 1. file builder
        YongFile yongFile = YongFile.insertFileBuilder()
                .yongFileGroup(fileGroup)
                .fileName(yongFileDTO.getFileName())
                .filePath(yongFileDTO.getFilePath())
                .fileSize(yongFileDTO.getFileSize())
                .fileType(yongFileDTO.getFileType())
                .build();

        // 2. save it
        YongFile saveFile = yongFileRepository.save(yongFile);

        // 3. return id
        return saveFile.getId();
    }

    @Override
    public YongFileVO show(Long id) {
        YongFile findFile = yongFileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no file"));
        return modelMapper.map(findFile, YongFileVO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongFileDTO yongFileDTO) {

        if(yongFileDTO.getYongFileGroupId() == null) throw new NullPointerException("file group id is null");

        // 1. find file group
        YongFileGroup fileGroup = fileGroupRepository.findById(yongFileDTO.getYongFileGroupId()).orElseThrow(() -> new NoSuchElementException("there is no file group"));

        // 2. then use update method
        YongFile findFile = yongFileRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file"));

        findFile.updateFile(yongFileDTO, fileGroup);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongFile yongFile = yongFileRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file"));
        yongFileRepository.delete(yongFile);
    }
}
