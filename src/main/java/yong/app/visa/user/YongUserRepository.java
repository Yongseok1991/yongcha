package yong.app.visa.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YongUserRepository extends JpaRepository<YongUser, Long> {
    YongUser findByUsername(String username);
}
