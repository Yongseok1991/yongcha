package yong.app.domain.user;

import yong.app.domain.role.YongRoleService;


/**
* @fileName EnumAuthorUserInfo
* @author yongseok
* @version 1.0.0
* @date 2023-05-02
* @summary 유저 권한 별 처리를 위한 enum 클래스 AuthorInjection 보조클래스 참고
**/


public enum YongAuthor {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    INSTANCE("INSTANCE");
    private YongRoleService yongRoleService;
    public final String config;

    public void setMyService(YongRoleService yongRoleService) {
        this.yongRoleService = yongRoleService;
    }
    private YongAuthor(String config) {
        this.config = config;
    }
    public YongUserDTO getUser(YongUserDTO yongUser) {
        switch (config) {
            case "ROLE_ADMIN": yongUser.addRole(INSTANCE.yongRoleService.findByRoleName("ROLE_ADMIN")); break;
            case "ROLE_USER": yongUser.addRole(INSTANCE.yongRoleService.findByRoleName("ROLE_USER")); break;
            case "ROLE_MANAGER": yongUser.addRole(INSTANCE.yongRoleService.findByRoleName("ROLE_MANAGER"));
        }
        return yongUser;
    }

}
