package yong.app.global.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import yong.app.domain.user.YongUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private static final long serialVersionUID = 1L;
    private YongUser user;
    private Map<String, Object> attributes;

    // 일반 시큐리티 로그인시 사용
    public PrincipalDetails(YongUser user) {
        this.user = user;
    }

    public PrincipalDetails(YongUser user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public YongUser getUser() {
        return user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        user.getRoles().forEach(role -> collect.add(new SimpleGrantedAuthority(role.getRoleName())));
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    @Override
    public String getName() {
        return (String) getAttributes().get("id");
    }
}
