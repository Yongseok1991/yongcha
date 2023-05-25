package yong.app.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YongRoleRepository extends JpaRepository<Role, Long> {

     // branch hong
     // branch test 22
     Role findByRoleName(String roleName);
}
