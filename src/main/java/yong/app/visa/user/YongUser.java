package yong.app.visa.user;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class YongUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    @CreationTimestamp
    private Timestamp regDt;
    @CreationTimestamp
    private Timestamp updtDt;

}
