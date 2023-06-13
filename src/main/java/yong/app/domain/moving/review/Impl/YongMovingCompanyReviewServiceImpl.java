package yong.app.domain.moving.review.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.base.YongFileCommon;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.file.group.YongFileGroupService;
import yong.app.domain.file.group.YongFileGroupVO;
import yong.app.domain.moving.company.YongMovingCompany;
import yong.app.domain.moving.company.YongMovingCompanyRepository;
import yong.app.domain.moving.review.*;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongMovingCompanyReviewServiceImpl implements YongMovingCompanyReviewService {

    private final YongMovingCompanyReviewRepository yongMovingCompanyReviewRepository;
    private final YongFileCommon yongFileCommon;
    private final YongFileGroupService yongFileGroupService;
    private final YongMovingCompanyRepository movingCompanyRepository;
    @Override
    public List<YongMovingCompanyReviewVO> list() {
        List<YongMovingCompanyReview> all = yongMovingCompanyReviewRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("review is empty");
        return all.stream().map(review -> new YongMovingCompanyReviewVO(review, null)).toList();
    }

    @Override
    public List<YongMovingCompanyReviewVO> listWithFiles() {
        List<YongMovingCompanyReview> all = yongMovingCompanyReviewRepository.findAll();
        if(all.isEmpty()) throw new NullPointerException("review is empty");
        return all.stream().map(review -> {
            YongFileGroupVO fileGroupVO = null;
            if(review.getYongFileGroupId() != null){
                fileGroupVO = yongFileGroupService.findFileGroupWithFiles(review.getYongFileGroupId());
            }
            return new YongMovingCompanyReviewVO(review, fileGroupVO);
        }).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongMovingCompanyReviewDTO companyReviewDTO) {

        YongMovingCompanyReview companyReview = YongMovingCompanyReview.insertReviewBuilder()
                .content(companyReviewDTO.getContent())
                .build();

        // 이사업체의 리뷰 -> 이사 업체 id 있어야 리뷰 등록 가능
        if(companyReviewDTO.getYongMovingCompanyId() == null) throw new IllegalStateException("moving company id cannot be null");
        else {
            YongMovingCompany movingCompany = movingCompanyRepository.findById(companyReviewDTO.getYongMovingCompanyId()).orElseThrow(() -> new NoSuchElementException("there is no moving company"));
            companyReview = companyReview.toBuilder().yongMovingCompany(movingCompany).build();
        }

        if(!companyReviewDTO.getAddFiles().isEmpty()){
            YongFileGroup fileGroup = yongFileCommon.addFiles(companyReviewDTO.getAddFiles(), null,0L);
            companyReview = companyReview.toBuilder().yongFileGroupId(fileGroup.getId()).build();
        }

        YongMovingCompanyReview save = yongMovingCompanyReviewRepository.save(companyReview);
        return save.getId();
    }

    @Override
    public YongMovingCompanyReviewVO show(Long id) {
        YongMovingCompanyReview companyReview = yongMovingCompanyReviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no review"));
        return new YongMovingCompanyReviewVO(companyReview, null);
    }

    @Override
    public YongMovingCompanyReviewVO showWithFiles(Long id) {
        YongMovingCompanyReview companyReview = yongMovingCompanyReviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no review"));
        YongFileGroupVO fileGroupWithFiles = yongFileGroupService.findFileGroupWithFiles(companyReview.getYongFileGroupId());
        return new YongMovingCompanyReviewVO(companyReview, fileGroupWithFiles);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongMovingCompanyReviewDTO companyReviewDTO) {
        // 1. review needs moving company
        if(companyReviewDTO.getYongMovingCompanyId() == null) throw new IllegalStateException("moving company id cannot be null");
        YongMovingCompany movingCompany = movingCompanyRepository.findById(companyReviewDTO.getYongMovingCompanyId()).orElseThrow(() -> new NoSuchElementException("there is no moving company"));

        // 2. find moving company review
        YongMovingCompanyReview companyReview = yongMovingCompanyReviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no moving company review"));

        // 3. if has file
        YongFileGroup fileGroup = null;
        if(companyReviewDTO.getYongFileGroupId() != null){
            fileGroup = yongFileCommon.addFiles(companyReviewDTO.getAddFiles(), companyReviewDTO.getDelFiles(), companyReviewDTO.getYongFileGroupId());
        }else if(companyReviewDTO.getYongFileGroupId() == null && !companyReviewDTO.getAddFiles().isEmpty()) {
            fileGroup = yongFileCommon.addFiles(companyReviewDTO.getAddFiles(), null, 0L);
        }

        companyReview.updateReview(companyReviewDTO, fileGroup, movingCompany);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongMovingCompanyReview companyReview = yongMovingCompanyReviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no moving company"));
        companyReview.removeReview();
    }
}
