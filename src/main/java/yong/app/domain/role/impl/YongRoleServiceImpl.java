package yong.app.domain.role.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yong.app.domain.role.Role;
import yong.app.domain.role.YongRoleRepository;
import yong.app.domain.role.YongRoleService;

@Service
@RequiredArgsConstructor
public class YongRoleServiceImpl implements YongRoleService {
    private final YongRoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
