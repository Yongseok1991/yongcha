package yong.app.global.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import yong.app.domain.user.YongAuthor;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserDTO;
import yong.app.domain.user.YongUserService;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final YongUserService yongUserService;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> userInfo = oauth2User.getAttributes();
        Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
        String username = provider + "_" + userInfo.get("id");
        String email = (String) kakaoAccount.get("email");

        /* TODO
        * 회원가입 처리
        * 있으면 로그인 , 없으면 회원가입
        * 넘기기.. username, email
        * user 빈 객체/builder -> set username, email => PrincipalDetails : 1 param으로 넘기기
        * */

        YongUser userEntity = yongUserService.findByUsername(username);
        if(userEntity == null) {
            YongUserDTO yongUserDTO = new YongUserDTO();
            yongUserDTO.setEmail(email);
            yongUserDTO.setUsername(username);
            userEntity = yongUserService.oAuthJoinProc(YongAuthor.ROLE_USER, yongUserDTO);
        } else {
            log.info("oauth2 kakao account already save");
        }
        return new PrincipalDetails(userEntity, userInfo);
    }
}
