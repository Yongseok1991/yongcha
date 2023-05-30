package yong.app.domain.code.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "yong_group_code")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongGroupCode extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_group_code_id")
    private Long id;

    private String name;

    private String description;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "insertGroupCodeBuilder")
    public YongGroupCode(String name, String description) {
        this.name = name;
        this.description = description;
        this.deleteYn = "N";
    }

    public void updateGroupCode(YongGroupCodeDTO yongGroupCodeDTO){
        if(yongGroupCodeDTO.getName() != null) this.name = yongGroupCodeDTO.getName();
        if(yongGroupCodeDTO.getDescription() != null) this.description = yongGroupCodeDTO.getDescription();
    }
    
    public void delteGroupCode(){
        this.deleteYn = "Y";
    }
}
