package yong.app.domain.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.auth.RoleType;
import yong.app.domain.auth.YongRole;
import yong.app.domain.auth.YongRoleRepository;
import yong.app.domain.auth.YongUsersRole;
import yong.app.domain.token.YongConfirmToken;
import yong.app.domain.token.YongConfirmTokenService;
import yong.app.domain.user.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class YongUserServiceImpl implements YongUserService {

    private final YongUserRepository yongUserRepository;
    private final YongRoleRepository yongRoleRepository;
    private final YongConfirmTokenService yongConfirmTokenService;
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

    public Optional<YongUser> getEmail(String email) {
        Optional<YongUser> byEmail = yongUserRepository.findByEmail(email);

//        Set<YongUsersRole> yongRoles = byEmail.get().getYongRoles();
//        for (YongUsersRole yongRole : yongRoles) {
//            log.info("yongRole : {} ", yongRole.getYongRole()); // 매핑 테이블 -> YongRole 테이블 접근 -> 필드 접근 -> 쿼리 수행됨
//        }

        return byEmail;
    }

    @Override
    @Transactional
    public Long join(YongUserDTO yongUserDTO) {

        List<YongRole> byRoleType = yongRoleRepository.findAllByRoleTypeIn(yongUserDTO.getRoleType())
                .stream().collect(Collectors.toList());

        if(byRoleType.isEmpty()){
            throw new NullPointerException("there is no role type");
        }
        // 권한 포함 insert
        YongUser yongUser = YongUser.joinProcBuilder()
                .email(yongUserDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(yongUserDTO.getPassword()))
                .yongRole(byRoleType)
                .build();
        YongUser savedUser = yongUserRepository.save(yongUser);

        // 가입 후 토큰 발급
        Long tokenId = yongConfirmTokenService.confirmTokenSave(savedUser.getId());

        // TODO [get] confirm url 생성 후 이메일 send
        return tokenId;
    }

    @Override
    @Transactional
    public String kakaoJoin(KakaoUserDTO kakaoUserDTO) {
        List<YongRole> byRoleType = yongRoleRepository.findAllByRoleTypeIn(List.of(RoleType.ROLE_USER));

        if(byRoleType.isEmpty()){
            throw new NullPointerException("there is no role type");
        }
        // 권한 포함 insert
        YongUser yongUser = YongUser.joinProcBuilder()
                .email(kakaoUserDTO.getEmail())
                .password(UUID.randomUUID().toString())
                .yongRole(byRoleType)
                .nickname(kakaoUserDTO.getNickname())
                .isEnabled(true)
                .build();
        YongUser savedUser = yongUserRepository.save(yongUser);
        return savedUser.getEmail();
    }

    public Optional<YongUser> getYongUserWithId(Long id) {
        return yongUserRepository.findById(id);
    }

    // 인증되지 않은 사용자를 인증 상태로 변경
    @Override
    public int enabledYongUser(String email) {
        return yongUserRepository.enableYongUser(email);
    }

    @Override
    @Transactional
    public void update(YongUserDTO yongUserDTO) {
        YongUser byEmail = yongUserRepository.findByEmail(yongUserDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("there is no user"));

        List<YongRole> allByRoleTypeIn = yongRoleRepository.findAllByRoleTypeIn(yongUserDTO.getRoleType());
        byEmail.addAuthorCd(allByRoleTypeIn);
    }

    @Override
    @Transactional
    public void updateById(Long id, YongUserDTO yongUserDTO) {
        YongUser yongUser = yongUserRepository.findById(id).orElseThrow(() -> new NoSuchElementException("there is no user"));
        getRoles(yongUserDTO, yongUser);
    }

    private void getRoles(YongUserDTO yongUserDTO, YongUser yongUser) {
        List<YongRole> allByRoleTypeIn = yongRoleRepository.findAllByRoleTypeIn(yongUserDTO.getRoleType());

        Set<YongUsersRole> yongRoles = yongUser.getYongRoles();
        for(YongUsersRole yr : yongRoles){
            RoleType roleType = yr.getYongRole().getRoleType();
            if(yongUserDTO.getRoleType().contains(roleType))  throw new IllegalStateException("user already has " + roleType);
        }

        yongUser.addAuthorCd(allByRoleTypeIn);
    }

    @Override
    @Transactional
    public void updateByLoginEmail(String email, YongUserDTO yongUserDTO) {
        YongUser byEmail = yongUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("there is no user"));
        getRoles(yongUserDTO, byEmail);
    }


    // 토큰 검증 후 사용자 인증
    @Override
    @Transactional
    public void confirmToken(String token) {
        YongConfirmToken byToken = yongConfirmTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token is not found."));

        if (byToken.getConfirmTime() != null) {
            throw new IllegalStateException("email already confirmed");
            // TODO 바로 로그인 후 메인 페이지로 보내기
        }

        LocalDateTime expiredTime = byToken.getExpiredTime();

        if (expiredTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
            // TODO token 교체 발급 후 이메일 다시 전송하는 로직
        }
        yongConfirmTokenService.changeConfirmedTime(token);
        this.enabledYongUser(byToken.getYongUser().getEmail());
    }



}
