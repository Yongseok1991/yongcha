package yong.app.global.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import yong.app.domain.auth.YongRole;
import yong.app.domain.auth.YongUsersRole;
import yong.app.domain.user.YongUser;
import yong.app.domain.user.YongUserService;

import java.util.*;

public class PrincipalDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private YongUser user;

    // 일반 시큐리티 로그인시 사용
    public PrincipalDetails(YongUser user) {
        this.user = user;
    }

    public YongUser getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        user.getYongRoles().forEach(role ->
                collect.add(new SimpleGrantedAuthority(role.getYongRole().getRoleType().toString())));
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
