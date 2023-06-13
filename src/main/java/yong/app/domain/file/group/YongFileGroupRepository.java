package yong.app.domain.file.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface YongFileGroupRepository extends JpaRepository<YongFileGroup, Long> {

    Optional<YongFileGroup> findByIdAndDeleteYnIs(Long id, String deleteYn);
}

