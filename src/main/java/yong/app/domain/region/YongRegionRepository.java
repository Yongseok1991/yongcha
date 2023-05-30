package yong.app.domain.region;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface YongRegionRepository extends JpaRepository<YongRegion, Long> {

    List<YongRegion> findByYongUserEmail(String email);

    Optional<YongRegion> findByIdAndYongUserEmail(Long id, String email);
}
