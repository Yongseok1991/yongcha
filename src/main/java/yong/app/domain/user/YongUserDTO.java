package yong.app.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import yong.app.domain.auth.RoleType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
public class YongUserDTO {
    @NotBlank @Email
    private String email;
    private String password;
    private List<RoleType> roleType;
}
