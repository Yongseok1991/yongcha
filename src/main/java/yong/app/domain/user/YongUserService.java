package yong.app.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yong.app.domain.role.RoleRepository;



/**
* @fileName YongUserService
* @author yongseok
* @version 1.0.0
* @date 2023-05-02
* @summary service레이어
**/

@Service
@RequiredArgsConstructor
public class YongUserService {

    private final RoleRepository roleRepository;
    private final YongUserRepository yongUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public YongUser joinProc(EnumAuthorUserInfo authorEnum, YongUserDTO bean) {

        String rawPassword = bean.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        bean.setPassword(encPassword);
        return yongUserRepository.save(authorEnum.getUser(bean).toEntity(bean));
    }

}
