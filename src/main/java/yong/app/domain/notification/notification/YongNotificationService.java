package yong.app.domain.notification.notification;

import java.util.List;

public interface YongNotificationService {

    List<YongNotificationVO> list();

    Long join(String email, YongNotificationDTO yongNotificationDTO);

    YongNotificationVO showById(Long id);

    List<YongNotificationVO> showUserNotify(String email);

    void updateById(Long id, YongNotificationDTO yongNotificationDTO);

    void delete(Long id);
}
