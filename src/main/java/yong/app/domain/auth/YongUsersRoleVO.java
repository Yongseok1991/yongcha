package yong.app.domain.auth;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.user.YongUserVO;

@Getter @Setter
public class YongUsersRoleVO {

    private Long id;
    private YongRoleVO yongRole;
    private YongUserVO yongUser;
}
