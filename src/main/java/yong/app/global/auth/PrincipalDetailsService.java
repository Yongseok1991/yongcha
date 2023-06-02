package yong.app.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.auth.YongUsersRole;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserService;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final YongUserService yongUserService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        return yongUserService.getEmail(email)
                .map(yongUser -> {
                    yongUser.getYongRoles().forEach(role -> role.getYongRole().getRoleType());
                    return new PrincipalDetails(yongUser);
                })
                .orElseThrow(() -> new IllegalStateException("email is not found"));
    }
}
