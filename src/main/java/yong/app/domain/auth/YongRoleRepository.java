package yong.app.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface YongRoleRepository extends JpaRepository<YongRole, Long> {


    List<YongRole> findAllByRoleTypeIn(Set<RoleType> roleTypes);
}
