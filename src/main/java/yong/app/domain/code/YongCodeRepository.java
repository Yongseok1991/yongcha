package yong.app.domain.code;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YongCodeRepository extends JpaRepository<YongCode, Long> {

    List<YongCode> findAllByParentIsNull();
}
