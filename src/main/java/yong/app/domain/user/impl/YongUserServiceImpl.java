package yong.app.domain.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import yong.app.domain.auth.RoleType;
import yong.app.domain.auth.YongRole;
import yong.app.domain.auth.YongRoleRepository;
import yong.app.domain.auth.YongUsersRole;
import yong.app.domain.user.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class YongUserServiceImpl implements YongUserService {

    private final YongUserRepository yongUserRepository;
    private final YongRoleRepository yongRoleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    @Override
    public List<YongUserVO> list() {
        List<YongUser> all = yongUserRepository.findAll();
        return all.stream().map(yongUser -> modelMapper.map(yongUser, YongUserVO.class)).collect(Collectors.toList());
    }

    @Override
    public YongUserVO show(Long id) {
        YongUser findUser = yongUserRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no user"));
        return modelMapper.map(findUser, YongUserVO.class);
    }

    public Optional<YongUser> findByEmail(String email) {
        Optional<YongUser> byEmail = yongUserRepository.findByEmail(email);

//        Set<YongUsersRole> yongRoles = byEmail.get().getYongRoles();
//        for (YongUsersRole yongRole : yongRoles) {
//            log.info("yongRole : {} ", yongRole.getYongRole()); // 매핑 테이블 -> YongRole 테이블 접근 -> 필드 접근 -> 쿼리 수행됨
//        }

        return byEmail;
    }

    @Override
    @Transactional(readOnly = false)
    public Long join(YongUserDTO yongUserDTO) {

        List<YongRole> byRoleType = yongRoleRepository.findAllByRoleTypeIn(yongUserDTO.getRoleType())
                                                        .stream().collect(Collectors.toList());

        if(byRoleType.isEmpty()){
            throw new NullPointerException("there is no role type");
        }

        YongUser yongUser = YongUser.joinProcBuilder()
                .email(yongUserDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(yongUserDTO.getPassword()))
                .yongRole(byRoleType)
                .build();

        YongUser save = yongUserRepository.save(yongUser);
        return save.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public void update(YongUserDTO yongUserDTO) {
        YongUser byEmail = yongUserRepository.findByEmail(yongUserDTO.getEmail())
                                             .orElseThrow(() -> new UsernameNotFoundException("there is no user"));

        List<YongRole> allByRoleTypeIn = yongRoleRepository.findAllByRoleTypeIn(yongUserDTO.getRoleType());
        byEmail.addAuthorCd(allByRoleTypeIn);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateById(Long id, YongUserDTO yongUserDTO) {
        YongUser yongUser = yongUserRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no user"));
        List<YongRole> allByRoleTypeIn = yongRoleRepository.findAllByRoleTypeIn(yongUserDTO.getRoleType());

        Set<YongUsersRole> yongRoles = yongUser.getYongRoles();
        for(YongUsersRole yr : yongRoles){
            RoleType roleType = yr.getYongRole().getRoleType();
            if(yongUserDTO.getRoleType().contains(roleType))  throw new IllegalStateException("user already has " + roleType);
        }

        yongUser.addAuthorCd(allByRoleTypeIn);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateByLoginEmail(String email, YongUserDTO yongUserDTO) {
        YongUser byEmail = yongUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("there is no user"));
        List<YongRole> allByRoleTypeIn = yongRoleRepository.findAllByRoleTypeIn(yongUserDTO.getRoleType());

        Set<YongUsersRole> yongRoles = byEmail.getYongRoles();
        for(YongUsersRole yr : yongRoles){
            RoleType roleType = yr.getYongRole().getRoleType();
            if(yongUserDTO.getRoleType().contains(roleType))  throw new IllegalStateException("user already has " + roleType);
        }

        byEmail.addAuthorCd(allByRoleTypeIn);
    }
}
