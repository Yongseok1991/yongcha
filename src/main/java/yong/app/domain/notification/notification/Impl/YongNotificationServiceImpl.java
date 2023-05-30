package yong.app.domain.notification.notification.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.notification.notification.YongNotification;
import yong.app.domain.notification.notification.YongNotificationRepository;
import yong.app.domain.notification.notification.YongNotificationService;
import yong.app.domain.notification.notification.YongNotificationVO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YongNotificationServiceImpl implements YongNotificationService {

    private final YongNotificationRepository yongNotificationRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<YongNotificationVO> list() {
        List<YongNotification> all = yongNotificationRepository.findAll();
        return all.stream().map(yongNotification -> modelMapper.map(yongNotification, YongNotificationVO.class)).collect(Collectors.toList());
    }
}
