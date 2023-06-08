package yong.app.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import yong.app.domain.auth.YongRole;
import yong.app.domain.auth.YongUsersRole;
import yong.app.domain.base.Address;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "yong_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class YongUser extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "yong_user_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "yongUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<YongUsersRole> yongRoles = new HashSet<>();

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(name = "noti_yn")
    private String notiYn;

    @Column(name = "enabled_yn")
    @ColumnDefault("false")
    private Boolean isEnabled;
    @Builder(builderMethodName = "joinProcBuilder")
    public YongUser(String email, List<YongRole> yongRole, String password, String nickname, Boolean isEnabled) {
       //
        for(YongRole yr : yongRole) {
            YongUsersRole yongUsersRole = YongUsersRole.joinAuthUserBuilder()
                    .yongRole(yr)
                    .yongUser(this)
                    .build();
            this.yongRoles.add(yongUsersRole);
        }
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isEnabled = isEnabled;
    }

    // 권한 추가 메소드
    public void addAuthorCd(List<YongRole> yongRoles) {
        for (YongRole yongRole : yongRoles) {
            YongUsersRole yongUsersRole = YongUsersRole.joinAuthUserBuilder()
                    .yongRole(yongRole)
                    .yongUser(this)
                    .build();
            this.yongRoles.add(yongUsersRole);
        }
    }

    public void addYongRoles(YongUsersRole yongRoles) {
            this.yongRoles.add(yongRoles);
    }

}
