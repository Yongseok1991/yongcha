package yong.app.domain.notification.notification;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.domain.notification.type.YongNotificationType;
import yong.app.domain.user.YongUser;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "yong_noticiation")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongNotification extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_user_id")
    private YongUser yongUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_notification_type_id")
    private YongNotificationType yongNotificationType;

    private String message;

    @Column(name = "read")
    private String read;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "reference_url")
    private String referenceUrl;

    @Builder(builderMethodName = "insertNotificationBuilder")
    public YongNotification(YongUser yongUser, YongNotificationType yongNotificationType, String message, String read, String referenceId, String referenceUrl) {
        this.yongUser = yongUser;
        this.yongNotificationType = yongNotificationType;
        this.message = message;
        this.read = read;
        this.referenceId = referenceId;
        this.referenceUrl = referenceUrl;
    }

    public void updateNotification(YongNotificationDTO notifyDTO){
        if(notifyDTO.getYongNotificationType() != null) this.yongNotificationType = notifyDTO.getYongNotificationType();
        if(notifyDTO.getMessage() != null) this.message = notifyDTO.getMessage();
        if(notifyDTO.getRead() != null) this.read = notifyDTO.getRead();
        if(notifyDTO.getReferenceId() != null) this.referenceId = notifyDTO.getReferenceId();
        if(notifyDTO.getReferenceUrl() != null) this.referenceUrl = notifyDTO.getReferenceUrl();
    }
}
