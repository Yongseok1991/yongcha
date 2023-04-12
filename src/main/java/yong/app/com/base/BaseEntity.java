package yong.app.com.base;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용 에너테이션
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용 -> 부모 클래스를 상속받는 자식 클래서에 매핑 정보만 제공
@Getter
@Setter
public abstract class BaseEntity {
    @CreatedDate // 엔티티가 생성되어 저장될 떄 시간을 자동으로 저장
    @Column(updatable = false) // 업데이트 불가
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdTime;
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedTime;
}
