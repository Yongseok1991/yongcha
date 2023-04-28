package yong.app.global.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용 에너테이션
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용 -> 부모 클래스를 상속받는 자식 클래서에 매핑 정보만 제공
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private Long regId;

    @LastModifiedBy
    private Long updtId;


    @Override
    public void onPrePersist(){
        this.regId = 1L;
        this.updtId = this.regId;
    }

    @Override
    public void onPreUpdate(){
        this.regId = 1L;
    }

}
