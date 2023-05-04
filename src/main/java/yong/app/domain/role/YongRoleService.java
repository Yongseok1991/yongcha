package yong.app.domain.role;


import org.springframework.stereotype.Service;

public interface YongRoleService {
    public Role findByRoleName(String roleName);
}
