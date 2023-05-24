package yong.app.domain.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import yong.app.domain.auth.RoleType;
import yong.app.domain.auth.YongUsersRole;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserForm {

    private String email;
//    @JsonIgnore
    private String password;

    private List<RoleType> roleType;

}
