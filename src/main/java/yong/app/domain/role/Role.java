package yong.app.domain.role;


import lombok.Getter;
import lombok.Setter;
import yong.app.domain.user.YongUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="app_role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="role_name")
    private String roleName;

    @Column(name="description")
    private String description;
    @ManyToMany(mappedBy = "roles")
    private List<YongUser> users;

}
