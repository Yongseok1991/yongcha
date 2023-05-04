package yong.app.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YongUserRepository extends JpaRepository<YongUser, Long> {
    YongUser findByUsername(String username);
    Boolean existsByUsername(String username);
}
