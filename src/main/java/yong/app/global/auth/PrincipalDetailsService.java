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
        Optional<YongUser> byEmail = yongUserService.getEmail(email);

        if(byEmail.isPresent()) {
            YongUser yongUser = byEmail.get();
            Set<YongUsersRole> yongRoles = yongUser.getYongRoles();
            for (YongUsersRole yongRole : yongRoles) {
                yongRole.getYongRole().getRoleType();
            }
        }
        return new PrincipalDetails(byEmail.get());
    }
}
