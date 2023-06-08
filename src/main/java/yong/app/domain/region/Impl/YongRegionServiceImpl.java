package yong.app.domain.region.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.region.*;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class YongRegionServiceImpl implements YongRegionService {

    private final YongRegionRepository yongRegionRepository;
    private final YongUserRepository yongUserRepository;

    @Override
    public List<YongRegionVO> list() {
        List<YongRegion> all = yongRegionRepository.findAll();
        return all.stream().map(YongRegionVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Long joinByLoginEmail(String email, YongRegionDTO yongRegionDTO) {

        List<YongRegion> byYongUserEmail = yongRegionRepository.findByYongUserEmail(email);
        if(byYongUserEmail.size() == 2) throw new IllegalStateException("user can get 2 region");

        YongUser yongUser = yongUserRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("there is no user"));

        YongRegion yongRegion = YongRegion.insertYongRegionBuilder()
                .regionCode(yongRegionDTO.getRegionCode())
                .latitude(yongRegionDTO.getLatitude())
                .langitude(yongRegionDTO.getLangitude())
                .yongUser(yongUser)
                .build();

        YongRegion save = yongRegionRepository.save(yongRegion);

        return save.getId();
    }

    @Override
    public List<YongRegionVO> showByLoginEmail(String email) {
        List<YongRegion> byYongUserEmail = yongRegionRepository.findByYongUserEmail(email);
        return byYongUserEmail.stream().map(YongRegionVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateByLoginEmail(Long id, String email, YongRegionDTO yongRegionDTO) {
        YongRegion findRegion = yongRegionRepository.findByIdAndYongUserEmail(id, email).orElseThrow(() -> new NoSuchElementException("there is no region"));;
        findRegion.updateYongRegion(yongRegionDTO);
    }

    @Override
    public void deleteByLoginEmail(Long id, String email) {
        YongRegion findRegion = yongRegionRepository.findByIdAndYongUserEmail(id, email).orElseThrow(() -> new NoSuchElementException("there is no region"));;
        yongRegionRepository.delete(findRegion);
    }
}
