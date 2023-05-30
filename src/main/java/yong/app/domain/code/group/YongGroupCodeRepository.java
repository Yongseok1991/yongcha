package yong.app.domain.code.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YongGroupCodeRepository extends JpaRepository<YongGroupCode, Long> {

    Optional<YongGroupCode> findByIdAndDeleteYnIs(Long id, String deleteYn);
}
