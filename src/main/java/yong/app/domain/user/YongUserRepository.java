package yong.app.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface YongUserRepository extends JpaRepository<YongUser, Long> {

//    @Query("select  y, yr from YongUser y left join fetch y.yongRoles yr where y.email = :email")
    Optional<YongUser> findByEmail(String email);
    @Modifying
    @Query("UPDATE YongUser y " +
            "SET y.isEnabled = TRUE WHERE y.email = :email")
    int enableAppUser(@Param("email")String email);
}
