package yong.app.domain.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.auth.RoleType;
import yong.app.domain.auth.YongRole;
import yong.app.domain.auth.YongRoleRepository;
import yong.app.domain.auth.YongUsersRole;
import yong.app.domain.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Transactional  // 트랜잭션 필요함.
    public Optional<YongUser> findByEmail(String email) {
        Optional<YongUser> byEmail = yongUserRepository.findByEmail(email);

        Set<YongUsersRole> yongRoles = byEmail.get().getYongRoles();
        for(YongUsersRole yr : yongRoles){
            yr.getYongRole().getRoleType();         // 매핑 테이블 -> YongRole 테이블 접근 -> 필드 접근 -> 쿼리 수행됨
        }

        int size = byEmail.get().getYongRoles().size();
        System.out.println("size = " + size);

        return byEmail;
    }

    @Override
    @Transactional
    public Long join(UserForm userForm) {

        List<YongRole> byRoleType = yongRoleRepository.findAllByRoleTypeIn(userForm.getRoleType())
                                                        .stream().collect(Collectors.toList());

        if(byRoleType.isEmpty()){
            throw new NullPointerException("there is no role type");
        }

        YongUser yongUser = YongUser.joinProcBuilder()
                .email(userForm.getEmail())
                .password(bCryptPasswordEncoder.encode(userForm.getPassword()))
                .yongRole(byRoleType)
                .build();

        YongUser save = yongUserRepository.save(yongUser);
        return save.getId();
    }

    @Override
    @Transactional
    public void update(UserForm userForm) {
        YongUser byEmail = yongUserRepository.findByEmail(userForm.getEmail())
                                             .orElseThrow(() -> new UsernameNotFoundException("there is no user"));
        List<YongRole> allByRoleTypeIn = yongRoleRepository.findAllByRoleTypeIn(userForm.getRoleType());
        byEmail.addAuthorCd(allByRoleTypeIn);
    }

    @Override
    public List<RoleType> findRoleTypeByEmail(String email) {
        List<RoleType> roleTypes = new ArrayList<>();

        Optional<YongUser> byEmail = yongUserRepository.findByEmail(email);

        Set<YongUsersRole> yongRoles = byEmail.get().getYongRoles();
        for(YongUsersRole yr : yongRoles){
            System.out.println("yr : " + yr.getYongRole());
            roleTypes.add(yr.getYongRole().getRoleType());         // 매핑 테이블 -> YongRole 테이블 접근 -> 필드 접근 -> 쿼리 수행됨
        }

        int size = byEmail.get().getYongRoles().size();
        return roleTypes;
    }


}
