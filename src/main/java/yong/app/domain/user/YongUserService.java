package yong.app.domain.user;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import yong.app.domain.auth.RoleType;

import java.util.List;
import java.util.Optional;


public interface YongUserService {
<<<<<<< HEAD

    Optional<YongUser> findByEmail(String email);

    Long join(UserForm userForm);

    void update(UserForm userForm);

    List<RoleType> findRoleTypeByEmail(String email);

=======
    YongUser joinProc(YongAuthor authorEnum, YongUserDTO bean);
    YongUser oAuthJoinProc(YongAuthor authorEnum, YongUserDTO bean);
    YongUser findByUsername(String username);
    List<YongUser> findAll();

    Boolean checkUsernameDuplicate(String username);
>>>>>>> 0e1214fc03bcd69231a9b91c0e081f0ff33e16c5
}
