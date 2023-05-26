package yong.app.domain.file.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class YongFileServiceImpl implements YongFileService {

    private final YongFileRepository yongFileRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongFileVO> findAll() {

        List<YongFile> findAll = yongFileRepository.findAll();
        return findAll.stream().map(file -> modelMapper.map(file, YongFileVO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long insert(YongFileDTO yongFileDTO) {
        // 1. file builder

        // 2. if dto has parent id -> find it -> if aprent is not null, then set

        // 3. save it

        // 4. return id
        return null;
    }
}
