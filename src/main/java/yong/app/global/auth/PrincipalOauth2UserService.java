package yong.app.global.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> userInfo = oauth2User.getAttributes();
        Map<String, Object> kakaoAccount = oauth2User.getAttribute("kakao_account");
        String username = provider + "_" + userInfo.get("id");
        String email = (String) kakaoAccount.get("email");

        log.info("username: {}, email: {}", username, email);
        log.info("attributes: {}", userInfo);
        return super.loadUser(userRequest);
    }
}
