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
    private YongNotificationTypeVO yongNotificationType;

    public YongNotificationVO(YongNotification yongNotification) {
        this.id = yongNotification.getId();
        this.message = yongNotification.getMessage();
        this.read = yongNotification.getRead();
        this.referenceId = yongNotification.getReferenceId();
        this.referenceUrl = yongNotification.getReferenceUrl();
        this.yongNotificationType = new YongNotificationTypeVO(yongNotification.getYongNotificationType());
    }
}
