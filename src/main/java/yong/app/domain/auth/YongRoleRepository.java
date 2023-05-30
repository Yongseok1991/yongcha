package yong.app.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YongRoleRepository extends JpaRepository<YongRole, Long> {


    List<YongRole> findAllByRoleTypeIn(List<RoleType> roleTypes);
}
