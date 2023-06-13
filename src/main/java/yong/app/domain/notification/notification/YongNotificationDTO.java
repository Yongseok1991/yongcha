package yong.app.domain.notification.notification;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.notification.type.YongNotificationType;

@Getter @Setter
public class YongNotificationDTO {

    private Long yongNotificationTypeId;
    private String message;
    private String read;
    private String referenceId;
    private String referenceUrl;

}
