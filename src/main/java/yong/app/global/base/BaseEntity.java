package yong.app.global.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import yong.app.domain.user.YongUser;
import yong.app.global.auth.PrincipalDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용 에너테이션
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용 -> 부모 클래스를 상속받는 자식 클래서에 매핑 정보만 제공
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    @Schema(name = "작성자", description = "작성자" , hidden = true)
    private Long regId;

    @LastModifiedBy
    @Schema(name = "수정자", description = "수정자" , hidden = true)
    private Long updtId;

    public void addUserKey(YongUser user) {
        this.regId = user.getId();
        this.updtId = user.getId();
    }
}
