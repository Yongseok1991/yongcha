package yong.app.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final YongUserRepository yongUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        YongUser user = yongUserRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("UserName: " + username);
        }
        return new PrincipalDetails(user);
    }
}