package yong.app.domain.user;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import yong.app.domain.auth.RoleType;

import java.util.List;
import java.util.Optional;


public interface YongUserService {

    List<YongUserVO> list(); // show user list

    YongUserVO show(Long id); // show single user info

    Optional<YongUser> findByEmail(String email); // use for login

    Long join(YongUserDTO yongUserDTO); // new login

    void update(YongUserDTO yongUserDTO);

    void updateById(Long id, YongUserDTO yongUserDTO); // update user by id
    void updateByLoginEmail(String email, YongUserDTO yongUserDTO); // update user by login info

}
