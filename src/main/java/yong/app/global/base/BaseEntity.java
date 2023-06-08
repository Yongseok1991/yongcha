package yong.app.global.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yong.app.domain.user.YongUser;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

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

}
