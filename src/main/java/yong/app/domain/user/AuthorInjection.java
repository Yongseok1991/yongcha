package yong.app.domain.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yong.app.domain.role.YongRoleRepository;
import yong.app.domain.role.YongRoleService;

import javax.annotation.PostConstruct;

/**
* @fileName AuthorInjection
* @author yongseok
* @version 1.0.0
* @date 2023-05-02
* @summary EnumAuthorUserInfo 서브 클래스 enum에 의존성 주입을 위한 클래스(RoleRepository)
**/

@Component
public class AuthorInjection {

    @Autowired
    private YongRoleService yongRoleService;

    private YongAuthor authorUserInfo;
    @PostConstruct
    public void init() {
        authorUserInfo = YongAuthor.INSTANCE;
        authorUserInfo.setMyService(yongRoleService);
    }

    public YongAuthor getInstance() {
        return authorUserInfo;
    }
}
