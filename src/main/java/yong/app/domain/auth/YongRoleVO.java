package yong.app.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class YongRoleVO {

    private Long id;
    private RoleType roleType;

    public YongRoleVO(YongRole yongRole) {
        this.id = yongRole.getId();
        this.roleType = yongRole.getRoleType();
    }
}
