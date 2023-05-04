package yong.app.domain.role;


import org.springframework.stereotype.Service;

public interface YongRoleService {
    Role findByRoleName(String roleName);
}
