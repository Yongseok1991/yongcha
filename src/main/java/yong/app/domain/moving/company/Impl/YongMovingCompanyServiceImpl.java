package yong.app.domain.moving.company.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.base.YongFileCommon;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.moving.company.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongMovingCompanyServiceImpl implements YongMovingCompanyService {

    private final YongMovingCompanyRepository movingCompanyRepository;
    private final ModelMapper modelMapper;
    private final YongFileCommon yongFileCommon;

    @Override
    public List<YongMovingCompanyVO> list() {
        List<YongMovingCompany> all = movingCompanyRepository.findAll();
        return all.stream().map(moving -> modelMapper.map(moving, YongMovingCompanyVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongMovingCompanyDTO movingDTO) {

        YongMovingCompany movingCompany = YongMovingCompany.insertMovingCompanyBuilder()
                .name(movingDTO.getName())
                .description(movingDTO.getDescription())
                .build();

        if(!movingDTO.getAddFiles().isEmpty()){
            YongFileGroup fileGroup = yongFileCommon.addFiles(movingDTO.getAddFiles(), null,0L);
            movingCompany = movingCompany.toBuilder().yongFileGroupId(fileGroup.getId()).build();
        }

        YongMovingCompany save = movingCompanyRepository.save(movingCompany);
        return save.getId();
    }

    @Override
    public YongMovingCompanyVO show(Long id) {
        YongMovingCompany company = movingCompanyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no company"));
        return modelMapper.map(company, YongMovingCompanyVO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongMovingCompanyDTO movingDTO) {
        YongMovingCompany movingCompany = movingCompanyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no company"));

        if(movingDTO.getYongFileGroupId() != null){
            YongFileGroup fileGroup = yongFileCommon.addFiles(movingDTO.getAddFiles(), movingDTO.getDelFiles(), movingDTO.getYongFileGroupId());
            movingDTO.setYongFileGroupId(fileGroup.getId());
        }else if(!movingDTO.getAddFiles().isEmpty()){
            YongFileGroup fileGroup = yongFileCommon.addFiles(movingDTO.getAddFiles(), null, 0L);
            movingDTO.setYongFileGroupId(fileGroup.getId());
        }
        movingCompany.updateMovingCompany(movingDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongMovingCompany movingCompany = movingCompanyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no company"));
        movingCompany.deleteMovingCompany();
    }
}
