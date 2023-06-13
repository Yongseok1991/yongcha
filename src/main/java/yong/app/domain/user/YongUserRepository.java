package yong.app.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface YongUserRepository extends JpaRepository<YongUser, Long> {

    Optional<YongUser> findByEmail(String email);
    Optional<YongUser> findById(Long id);

    // 비활성화 상태인 유저를 인증된 유저로 변경
    @Modifying
    @Query("""
            UPDATE YongUser y
              SET y.isEnabled = TRUE
            WHERE y.email = :email
            """
    )
    int enableYongUser(@Param("email")String email);
}
