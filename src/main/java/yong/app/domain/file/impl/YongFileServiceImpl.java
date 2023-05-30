package yong.app.domain.file.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongFileServiceImpl implements YongFileService {

    private final YongFileRepository yongFileRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongFileVO> list() {
        List<YongFile> findAll = yongFileRepository.findAllByParentIsNull();
        return findAll.stream().map(yongFile -> modelMapper.map(yongFile, YongFileVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongFileDTO yongFileDTO) {
        // 1. file builder
        YongFile yongFile = YongFile.insertFileBuilder()
                .fileName(yongFileDTO.getFileName())
                .description(yongFileDTO.getDescription())
                .build();

        // 2. if dto has parent id -> find it -> if aprent is not null, then set
        if(yongFileDTO.getParentFileId() != null){
            YongFile parentFile = yongFileRepository.findByIdAndDeleteYnIs(yongFileDTO.getParentFileId(), "N")
                    .orElseThrow(() -> new NoSuchElementException("there is no parent"));
            yongFile = yongFile.toBuilder().parent(parentFile).build();
        }

        // 3. save it
        YongFile saveFile = yongFileRepository.save(yongFile);

        // 4. return id
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
        // 1. if has parent -> find parent and set parent
        if(yongFileDTO.getParentFileId() != null){
            YongFile parentFile = yongFileRepository.findByIdAndDeleteYnIs(yongFileDTO.getParentFileId(), "N")
                    .orElseThrow(() -> new NoSuchElementException("there is no parent"));
            yongFileDTO.setParent(parentFile);
        }

        // 2. then use update method
        YongFile findFile = yongFileRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file"));
        findFile.updateFile(yongFileDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongFile yongFile = yongFileRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no file"));

        // if has child -> can't delete parent
        if(!yongFile.getChild().isEmpty()){
            throw new IllegalArgumentException("there is child yon can't delete it");
        }
        yongFile.deleteFile();
    }
}
