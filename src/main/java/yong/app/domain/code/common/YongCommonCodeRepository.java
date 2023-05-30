package yong.app.domain.code.common;

import org.springframework.data.jpa.repository.JpaRepository;
import yong.app.domain.code.group.YongGroupCode;

import java.util.List;

public interface YongCommonCodeRepository extends JpaRepository<YongCommonCode, Long> {

    List<YongCommonCode> findAllByYongGroupCode(YongGroupCode yongGroupCode);
}
