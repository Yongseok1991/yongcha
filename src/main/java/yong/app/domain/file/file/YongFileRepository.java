package yong.app.domain.file.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface YongFileRepository extends JpaRepository<YongFile, Long> {
}
