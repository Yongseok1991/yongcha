package yong.app.domain.notification.type;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "yong_notification_type")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongNotificationType extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_notification_type_id")
    private Long id;

    private String name;
    private String description;


    @Builder(builderMethodName = "insertNotificationTypeBuilder")
    public YongNotificationType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateNotificationTpye(YongNotificationTypeDTO typeDTO){
        if(typeDTO.getName() != null) this.name = typeDTO.getName();
        if(typeDTO.getDescription() != null) this.description = typeDTO.getDescription();
    }
}
