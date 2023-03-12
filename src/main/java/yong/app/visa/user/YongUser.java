package yong.app.visa.user;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NotBlank
    private String role;
    @CreationTimestamp
    private Timestamp regDt;
    @CreationTimestamp
    private Timestamp updtDt;

}
