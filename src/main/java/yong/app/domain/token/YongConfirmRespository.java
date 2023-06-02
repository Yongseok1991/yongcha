package yong.app.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface YongConfirmRespository extends JpaRepository<YongConfirmToken, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE YongConfirmToken c " +
            "SET c.confirmTime = :confirmTime " +
            "WHERE c.token = :token")
    int updateConfirmedAt(
            @Param("token") String token,
            @Param("confirmTime") LocalDateTime confirmTime
    );
    Optional<YongConfirmToken> findByToken(String token);

}
