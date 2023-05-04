package yong.app.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YongRoleRepository extends JpaRepository<Role, Long> {

     // branch hong
     Role findByRoleName(String roleName);
}
