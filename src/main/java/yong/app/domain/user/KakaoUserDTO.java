package yong.app.domain.user;

import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.LinkedHashMap;
import java.util.Map;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserDTO {

    private String oauthName;
    private String nickname;
    private String email;
    private static final String PROVIDER = "kakao";
    private Map<String, Object> attributes;
    public KakaoUserDTO(OAuth2User oAuth2User) {
        // TODO null exception 고민...
        if (oAuth2User != null) {
            this.attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            assert kakaoAccount != null;
            LinkedHashMap profile = (LinkedHashMap) kakaoAccount.get("profile");
            this.email = (String) kakaoAccount.get("email");
            this.nickname = (String) profile.get("nickname");
            this.oauthName = PROVIDER + "_" + attributes.get("id");
        }
    }

}
