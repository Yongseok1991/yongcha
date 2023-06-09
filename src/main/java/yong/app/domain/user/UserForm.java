package yong.app.domain.user;


import lombok.Getter;
import lombok.Setter;
import yong.app.domain.auth.RoleType;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserForm {

    private String email;
//    @JsonIgnore
    private String password;

    private Set<RoleType> roleType;

}
