package yong.app.domain.auth;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String description;

    @Column(name = "use_yn")
    private String useYn;

}
