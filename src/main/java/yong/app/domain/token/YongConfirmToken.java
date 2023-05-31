package yong.app.domain.token;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yong.app.domain.user.YongUser;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
* @fileName YongConfirmToken
* @author yongseok
* @version 1.0.0
* @date 2023-05-31
* @summary  회원가입시 토큰 발행을 해서 이메일 인증에 사용하기 위한 테이블
**/

@Getter
@NoArgsConstructor
@Entity
public class YongConfirmToken extends BaseTimeEntity {
    @Column(name = "confirm_token_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiredTime;

    private LocalDateTime confirmTime;

    @ManyToOne
    @JoinColumn(name = "yong_user_id")
    private YongUser yongUser;

    @Builder(builderMethodName = "createTokenBuilder")
    public YongConfirmToken(String token, LocalDateTime expiredTime, LocalDateTime confirmTime, YongUser yongUser) {
        this.token = token;
        this.expiredTime = expiredTime;
        this.confirmTime = confirmTime;
        this.yongUser = yongUser;
    }
}
