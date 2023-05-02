package yong.app.domain.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

public interface SysCodeRepository extends JpaRepository<SysCode, Long> {
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("update SysCode syscode set " +
//            "syscode.code = coalesce(:#{#syscode.code}, syscode.code)" +
//            ",syscode.upperCode = coalesce(:#{#syscode.upperCode}, syscode.upperCode) " +
//            ",syscode.codeNm = coalesce(:#{#syscode.codeNm}, syscode.codeNm) " +
//            ",syscode.codeDc = coalesce(:#{#syscode.codeDc}, syscode.codeDc) " +
//            ",syscode.useAt = coalesce(:#{#syscode.useAt}, syscode.useAt)" +
//            ",syscode.lv = coalesce(:#{#syscode.lv}, syscode.lv)" +
//            ",syscode.rmDc = coalesce(:#{#syscode.rmDc}, syscode.rmDc)" +
//            ",syscode.sortNo = coalesce(:#{#syscode.sortNo}, syscode.sortNo)" +
//            "where syscode.id = :#{#syscode.id}")
//    SysCode updateCodeById(@Param("sysCode") SysCode sysCode);

}
