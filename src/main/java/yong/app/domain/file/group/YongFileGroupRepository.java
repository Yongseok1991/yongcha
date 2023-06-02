package yong.app.domain.file.group;

import org.springframework.data.jpa.repository.JpaRepository;
import yong.app.domain.file.file.YongFile;

import java.util.Optional;

public interface YongFileGroupRepository extends JpaRepository<YongFileGroup, Long> {

    Optional<YongFileGroup> findByIdAndDeleteYnIs(Long id, String deleteYn);

}

