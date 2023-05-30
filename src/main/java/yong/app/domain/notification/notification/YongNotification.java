package yong.app.domain.notification.notification;

import lombok.AccessLevel;
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

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "reference_url")
    private String referenceUrl;
}
