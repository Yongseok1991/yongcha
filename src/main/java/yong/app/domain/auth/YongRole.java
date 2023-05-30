package yong.app.domain.auth;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="yong_role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YongRole {

    @Id @GeneratedValue
    @Column(name ="yong_role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "yongRole")
    private List<YongUsersRole> yongUsersRoles = new ArrayList<>();

    private String description;

    @Column(name = "use_yn")
    private String useYn;

}
