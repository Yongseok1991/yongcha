package yong.app.domain.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yong.app.domain.role.Role;
import yong.app.domain.role.YongRoleService;
import yong.app.domain.user.*;

import java.util.List;
import java.util.UUID;


/**
* @fileName YongUserService
* @author yongseok
* @version 1.0.0
* @date 2023-05-02
* @summary service레이어
**/

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class YongUserServiceImpl implements YongUserService {

    private final YongUserRepository yongUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public YongUser joinProc(YongAuthor authorEnum, YongUserDTO bean) {

        String rawPassword = bean.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        bean.setPassword(encPassword);
        return yongUserRepository.save(authorEnum.getUser(bean).toEntity(bean));
    }

    public YongUser oAuthJoinProc(YongAuthor authorEnum, YongUserDTO bean) {
        UUID uuid = UUID.randomUUID();
        String encPassword = bCryptPasswordEncoder.encode(uuid.toString());
        bean.setPassword(encPassword);
        return yongUserRepository.save(authorEnum.getUser(bean).toEntity(bean));
    }

    public YongUser findByUsername(String username) {
        return yongUserRepository.findByUsername(username);
    }

    public List<YongUser> findAll() {
        return yongUserRepository.findAll();
    }

    @Override
    public Boolean checkUsernameDuplicate(String username) {
        return yongUserRepository.existsByUsername(username);
    }


}
