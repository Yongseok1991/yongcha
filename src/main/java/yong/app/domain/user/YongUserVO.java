package yong.app.domain.user;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.auth.YongRoleVO;
import yong.app.domain.auth.YongUsersRole;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class YongUserVO {
    private Long id;
    private String email;
    private String nickname;
    private Set<YongRoleVO> roles = new HashSet<>();

    public YongUserVO(YongUser yongUser) {
        this.id = yongUser.getId();
        this.email = yongUser.getEmail();
        this.nickname = yongUser.getNickname();
        for (YongUsersRole yongUsersRole : yongUser.getYongRoles()) {
            this.roles.add(new YongRoleVO(yongUsersRole.getYongRole()));
        }
    }
}
