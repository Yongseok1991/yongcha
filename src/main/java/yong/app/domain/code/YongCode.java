package yong.app.domain.code;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "yong_code")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_code_id")
    private Long id;

    private String name;

    private String description;

    @Column(name = "delete_yn")
    private String deleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private YongCode parent;

    @OneToMany(mappedBy = "parent")
    private List<YongCode> child = new ArrayList<>();

    @Builder(builderMethodName = "insertYongCodeBuidler", toBuilder = true)
    public YongCode(String name, String description, YongCode parent) {
        this.name = name;
        this.description = description;
        this.deleteYn = "N";
        this.parent = parent;
    }

    public void updateYongCode(YongCodeDTO yongCodeDTO, YongCode parentCode){
        if(yongCodeDTO.getName() != null) this.name = yongCodeDTO.getName();
        if(yongCodeDTO.getDescription() != null) this.description =  yongCodeDTO.getDescription();
        if(parentCode != null) this.parent = parentCode;
    }
}
