package yong.app.domain.file.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface YongFileRepository extends JpaRepository<YongFile, Long> {
    List<YongFile> findAllByYongFileGroupId(Long id);
}
