package yong.app.global.base;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용 에너테이션
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용 -> 부모 클래스를 상속받는 자식 클래서에 매핑 정보만 제공
@Getter
@Setter
public abstract class BaseTimeEntity {
    @CreatedDate // 엔티티가 생성되어 저장될 떄 시간을 자동으로 저장
    @Column(updatable = false) // 업데이트 불가
    private String createdTime;
    @LastModifiedDate
    private String modifiedTime;
    @PrePersist
    public void onPrePersist(){
        this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedTime = this.createdTime;
    }
    @PreUpdate
    public void onPreUpdate(){
        this.modifiedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
