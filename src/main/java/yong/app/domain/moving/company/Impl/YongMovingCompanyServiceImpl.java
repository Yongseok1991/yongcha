package yong.app.domain.moving.company.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import yong.app.domain.base.YongFileCommon;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.file.group.YongFileGroupService;
import yong.app.domain.file.group.YongFileGroupVO;
import yong.app.domain.moving.company.*;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongMovingCompanyServiceImpl implements YongMovingCompanyService {

    private final YongMovingCompanyRepository movingCompanyRepository;
    private final YongFileCommon yongFileCommon;
    private final YongFileGroupService yongFileGroupService;

    @Override
    public List<YongMovingCompanyVO> list() {
        List<YongMovingCompany> all = movingCompanyRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("moving company is empty");
        return all.stream().map(yongMovingCompany -> new YongMovingCompanyVO(yongMovingCompany, null)).toList();
    }

    @Override
    public List<YongMovingCompanyVO> listWithFiles() {
        List<YongMovingCompany> all = movingCompanyRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("moving company is empty");
        return all.stream().map(yongMovingCompany -> {
            YongFileGroupVO fileGroup = null;
            if(yongMovingCompany.getYongFileGroupId() != null){
                fileGroup = yongFileGroupService.findFileGroupWithFiles(yongMovingCompany.getYongFileGroupId());
            }
            return new YongMovingCompanyVO(yongMovingCompany, fileGroup);
        }).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongMovingCompanyDTO movingDTO) {

        YongMovingCompany movingCompany = YongMovingCompany.insertMovingCompanyBuilder()
                .name(movingDTO.getName())
                .description(movingDTO.getDescription())
                .build();

        if(!ObjectUtils.isEmpty(movingDTO.getAddFiles())){
            YongFileGroup fileGroup = yongFileCommon.addFiles(movingDTO.getAddFiles(), null,0L);
            movingCompany = movingCompany.toBuilder().yongFileGroupId(fileGroup.getId()).build();
        }

        YongMovingCompany save = movingCompanyRepository.save(movingCompany);
        return save.getId();
    }

    @Override
    public YongMovingCompanyVO show(Long id) {
        YongMovingCompany company = movingCompanyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no company"));
        return new YongMovingCompanyVO(company, null);
    }

    @Override
    public YongMovingCompanyVO showWithFiles(Long id) {
        YongMovingCompany company = movingCompanyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no company"));
        YongFileGroupVO fileGroupWithFiles = yongFileGroupService.findFileGroupWithFiles(company.getYongFileGroupId());
        return new YongMovingCompanyVO(company, fileGroupWithFiles);
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
