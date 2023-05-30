package yong.app.domain.user;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.auth.YongUsersRoleVO;

import java.util.Set;

@Getter @Setter
public class YongUserVO {

    private Long id;
    private String email;
    private Set<YongUsersRoleVO> yongRoles;
}
