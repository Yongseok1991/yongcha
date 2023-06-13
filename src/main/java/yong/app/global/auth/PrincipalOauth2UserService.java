package yong.app.global.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.user.KakaoUserDTO;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final YongUserService yongUserService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        KakaoUserDTO kakaoUserDTO = new KakaoUserDTO(oauth2User);
         return yongUserService.getEmail(kakaoUserDTO.getEmail())
                .map(existingUser -> {
                    existingUser.getYongRoles().forEach(role -> role.getYongRole().getRoleType());
                    return new PrincipalDetails(existingUser, kakaoUserDTO.getAttributes());
                })
                .orElseGet(() -> {
                    String kakaoEmail = yongUserService.kakaoJoin(kakaoUserDTO);
                    YongUser yongUser = yongUserService.getEmail(kakaoEmail)
                            .orElseThrow(() -> new IllegalStateException("email is not found"));
                    yongUser.getYongRoles().forEach(role -> role.getYongRole().getRoleType());
                    return new PrincipalDetails(yongUser, kakaoUserDTO.getAttributes());
                });
    }
}
