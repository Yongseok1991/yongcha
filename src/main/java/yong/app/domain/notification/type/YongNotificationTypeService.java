package yong.app.domain.notification.type;

import java.util.List;

public interface YongNotificationTypeService {

    List<YongNotificationTypeVO> list();

    Long join(YongNotificationTypeDTO yongNotificationTypeDTO);

    void update(Long id, YongNotificationTypeDTO yongNotificationTypeDTO);

    YongNotificationTypeVO show(Long id);
}
