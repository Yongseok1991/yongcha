package yong.app.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YongUserRepository extends JpaRepository<YongUser, Long> {
    Optional<YongUser> findByEmail(String email);

}
