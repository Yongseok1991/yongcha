package yong.app.domain.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yong.app.domain.role.Role;
import yong.app.global.base.BaseTimeEntity;

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

@Getter
@Setter
@NoArgsConstructor
public class YongUserDTO {

    private Long uid;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Builder
    public YongUserDTO(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public YongUser toEntity(YongUserDTO dto) {
        YongUser yongUser = YongUser
                .builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(dto.getRoles())
                .build();
        return yongUser;
    }

}
