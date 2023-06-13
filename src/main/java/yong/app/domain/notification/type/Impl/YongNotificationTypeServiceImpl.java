package yong.app.domain.notification.type.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.notification.type.*;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongNotificationTypeServiceImpl implements YongNotificationTypeService {

    private final YongNotificationTypeRepository yongNotificationTypeRepository;

    @Override
    public List<YongNotificationTypeVO> list() {
        List<YongNotificationType> all = yongNotificationTypeRepository.findAll();
        return all.stream().map(YongNotificationTypeVO::new).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongNotificationTypeDTO yongNotificationTypeDTO) {

        YongNotificationType yongNotificationType = YongNotificationType.insertNotificationTypeBuilder()
                .name(yongNotificationTypeDTO.getName())
                .description(yongNotificationTypeDTO.getDescription())
                .build();
        YongNotificationType save = yongNotificationTypeRepository.save(yongNotificationType);
        return save.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, YongNotificationTypeDTO yongNotificationTypeDTO) {
        YongNotificationType type = yongNotificationTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no notification type"));
        type.updateNotificationTpye(yongNotificationTypeDTO);
    }

    @Override
    public YongNotificationTypeVO show(Long id) {
        YongNotificationType type = yongNotificationTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no notification type"));
        return new YongNotificationTypeVO(type);
    }
}
