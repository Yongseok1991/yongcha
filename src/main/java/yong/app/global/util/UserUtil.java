package yong.app.global.util;

import org.springframework.security.core.Authentication;

public final class UserUtil {
    private UserUtil() {}

    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null
                && authentication.getPrincipal() != "anonymousUser";
    }

}
