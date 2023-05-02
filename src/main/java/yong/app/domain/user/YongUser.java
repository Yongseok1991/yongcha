package yong.app.domain.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import yong.app.global.base.BaseTimeEntity;
import yong.app.domain.role.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * @fileName YongUser
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-04-26
 * @summary swagger에 대한 예시 작성
 *         @Schema 추가 속성들 : nullable / defaultValue / example / maxLength / allowValues ...
 **/

@Entity
@Getter
@Setter
@Schema(description = "YongUser 엔티티")                             // ** entity에 대한 설명
public class YongUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "유저 uid", description = "유저의 uid를 보여주는 필드")      // ** 필드에 대한 설명
    private Long uid;

    @NotBlank
    @Schema(name = "유저 name", description = "유저의 name을 보여주는 필드")
    private String username;

    @NotBlank
    @Schema(name = "유저 password", description = "유저의 password를 보여주는 필드")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Schema(name = "유저 role", description = "유저의 role (YongUser와 M:M 관계)")
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
