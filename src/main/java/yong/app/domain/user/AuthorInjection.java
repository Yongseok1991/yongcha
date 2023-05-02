package yong.app.domain.user;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import yong.app.domain.role.RoleRepository;

import javax.annotation.PostConstruct;

@Component
public class AuthorInjection {

    @Autowired
    private  RoleRepository roleRepository;

    private EnumAuthorUserInfo authorUserInfo;
    @PostConstruct
    public void init() {
        authorUserInfo = EnumAuthorUserInfo.INSTANCE;
        authorUserInfo.setMyService(roleRepository);
    }

    public EnumAuthorUserInfo getInstance() {
        return authorUserInfo;
    }
}
