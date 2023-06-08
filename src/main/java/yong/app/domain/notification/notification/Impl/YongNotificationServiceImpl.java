package yong.app.domain.notification.notification.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.notification.notification.*;
import yong.app.domain.notification.type.YongNotificationType;
import yong.app.domain.notification.type.YongNotificationTypeRepository;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongNotificationServiceImpl implements YongNotificationService {

    private final YongNotificationRepository notifyRepositoty;
    private final YongNotificationTypeRepository notifyTypeRepository;
    private final YongUserRepository yongUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<YongNotificationVO> list() {
        List<YongNotification> all = notifyRepositoty.findAll();
        return all.stream().map(yongNotification -> modelMapper.map(yongNotification, YongNotificationVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(String email, YongNotificationDTO notifyDTO) {

        if(notifyDTO.getYongNotificationTypeId() == null) throw new NullPointerException("notification type is null");

        YongNotificationType type = notifyTypeRepository.findById(notifyDTO.getYongNotificationTypeId()).orElseThrow(() -> new NoSuchElementException("there is no notification type"));

        YongUser yongUser = yongUserRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("there is no user"));

        YongNotification yongNotification = YongNotification.insertNotificationBuilder()
                .yongUser(yongUser)
                .yongNotificationType(type)
                .message(notifyDTO.getMessage())
                .read(notifyDTO.getRead())
                .referenceId(notifyDTO.getReferenceId())
                .referenceUrl(notifyDTO.getReferenceUrl())
                .build();

        YongNotification save = notifyRepositoty.save(yongNotification);

        return save.getId();
    }

    @Override
    public YongNotificationVO showById(Long id) {
        YongNotification yongNotification = notifyRepositoty.findById(id).orElseThrow(() -> new NoSuchElementException("there is no notification"));
        return modelMapper.map(yongNotification, YongNotificationVO.class);
    }

    @Override
    public List<YongNotificationVO> showUserNotify(String email) {
        List<YongNotification> userNotifies = notifyRepositoty.findByYongUserEmail(email);
        return userNotifies.stream().map(notify -> modelMapper.map(notify, YongNotificationVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public void updateById(Long id, YongNotificationDTO notifyDTO) {
        if(notifyDTO.getYongNotificationTypeId() == null) throw new NullPointerException("notification type is null");

        YongNotificationType type = notifyTypeRepository.findById(notifyDTO.getYongNotificationTypeId()).orElseThrow(() -> new NoSuchElementException("there is no notification type"));


        YongNotification yongNotification = notifyRepositoty.findById(id).orElseThrow(() -> new NoSuchElementException("there is no notification"));
        notifyDTO.setYongNotificationType(type);

        yongNotification.updateNotification(notifyDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        YongNotification yongNotification = notifyRepositoty.findById(id).orElseThrow(() -> new NoSuchElementException("there is no notification"));
        notifyRepositoty.delete(yongNotification);
    }
}
