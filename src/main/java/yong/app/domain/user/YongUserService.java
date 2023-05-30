package yong.app.domain.user;


import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import yong.app.domain.auth.RoleType;


import java.util.List;
import java.util.Optional;


public interface YongUserService {

    List<YongUserVO> list();

    Optional<YongUser> findByEmail(String email);

    Long join(UserForm userForm);

    void update(UserForm userForm);

    List<RoleType> findRoleTypeByEmail(String email);

}
