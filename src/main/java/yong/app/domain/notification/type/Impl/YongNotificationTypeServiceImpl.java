package yong.app.domain.notification.type.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.notification.type.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongNotificationTypeServiceImpl implements YongNotificationTypeService {

    private final YongNotificationTypeRepository yongNotificationTypeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongNotificationTypeVO> list() {
        List<YongNotificationType> all = yongNotificationTypeRepository.findAll();
        return all.stream().map(yongNotificationType -> modelMapper.map(yongNotificationType, YongNotificationTypeVO.class)).collect(Collectors.toList());
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
        return modelMapper.map(type, YongNotificationTypeVO.class);
    }
}
