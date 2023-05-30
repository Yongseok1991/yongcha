package yong.app.domain.user;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.auth.RoleType;

import java.util.List;

@Getter @Setter
public class YongUserDTO {
    private String email;
    private String password;
    private List<RoleType> roleType;
}
