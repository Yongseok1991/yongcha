package yong.app.domain.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import yong.app.domain.role.Role;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

@ToString
@Getter
@Setter
@NoArgsConstructor
public class YongUserDTO {

    @Schema(hidden = true)
    private Long uid;
    private String username;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;
    @Schema(hidden = true)
    private Set<Role> roles = new HashSet<>();
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Builder
    public YongUserDTO(String username, String password, String email,Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public YongUser toEntity(YongUserDTO dto) {
        YongUser yongUser = YongUser
                .builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roles(dto.getRoles())
                .build();
        return yongUser;
    }

}
