package yong.app.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final YongUserService yongUserService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         return yongUserService.findByEmail(email)
                .map((user) -> {
                    PrincipalDetails principalDetails = new PrincipalDetails(user);
                    return principalDetails;
                })
                .orElseThrow(() -> new UsernameNotFoundException("email is not found"));
    }
}
