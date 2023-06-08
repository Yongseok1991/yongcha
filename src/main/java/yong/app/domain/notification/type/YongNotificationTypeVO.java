package yong.app.domain.notification.type;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class YongNotificationTypeVO {
    private Long id;
    private String name;
    private String description;

    public YongNotificationTypeVO(YongNotificationType yongNotificationType) {
        this.id = yongNotificationType.getId();
        this.name = yongNotificationType.getName();
        this.description = yongNotificationType.getDescription();
    }
}
