package yong.app.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserUtil {

    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null &&
                authentication.getPrincipal() != "anonymousUser";
    }

}
