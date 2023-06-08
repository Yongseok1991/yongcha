package yong.app.domain.notification.notification;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.notification.type.YongNotificationType;
import yong.app.domain.notification.type.YongNotificationTypeVO;
import yong.app.domain.user.YongUserVO;

@Getter @Setter
public class YongNotificationVO {

    private Long id;
    private String message;
    private String read;
    private String referenceId;
    private String referenceUrl;
    private YongUserVO yongUser;
    private YongNotificationTypeVO yongNotificationType;
}
