package yong.app.domain.role;


import org.springframework.stereotype.Service;

import java.util.List;

public interface YongRoleService {
    public Role findByRoleName(String roleName);

    List<Role> findAll();
}
