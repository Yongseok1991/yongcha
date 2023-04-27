package yong.app.global.base;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yong.app.domain.role.Role;
import yong.app.domain.role.RoleRepository;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class InitRolesTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void initRoles() {
        roleRepository.deleteAll();
        roleRepository.save(Role.builder().roleName("ROLE_USER").build());
        roleRepository.save(Role.builder().roleName("ROLE_ADMIN").build());
        roleRepository.save(Role.builder().roleName("ROLE_MANAGER").build());
    }
}
