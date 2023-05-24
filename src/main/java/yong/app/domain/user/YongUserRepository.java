package yong.app.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YongUserRepository extends JpaRepository<YongUser, Long> {
<<<<<<< HEAD
    Optional<YongUser> findByEmail(String email);

=======
    YongUser findByUsername(String username);
    Boolean existsByUsername(String username);
>>>>>>> 0e1214fc03bcd69231a9b91c0e081f0ff33e16c5
}
