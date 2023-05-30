package yong.app.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import yong.app.domain.post.category.YongPostCategory;

import java.util.List;
import java.util.Optional;

public interface YongFileRepository extends JpaRepository<YongFile, Long> {

    List<YongFile> findAllByParentIsNull();

    Optional<YongFile> findByIdAndDeleteYnIs(Long id, String deleteYn);
}
