package yong.app.domain.auth;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import yong.app.domain.user.YongUser;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "yong_users_role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YongUsersRole extends BaseTimeEntity {

    // 주석주석
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_users_role_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "yong_role_id")
    private YongRole yongRole;

    private String description;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "yong_user_id")
    private YongUser yongUser;



    @Builder(builderMethodName = "joinAuthUserBuilder")
    public YongUsersRole(YongRole yongRole, YongUser yongUser) {
        this.yongRole = yongRole;
        this.yongUser = yongUser;
    }
}
