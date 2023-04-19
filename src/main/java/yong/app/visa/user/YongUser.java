package yong.app.visa.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import yong.app.com.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class YongUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "유저 uid", description = "유저에 uid를 보여주는 필드")
    private Long uid;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
