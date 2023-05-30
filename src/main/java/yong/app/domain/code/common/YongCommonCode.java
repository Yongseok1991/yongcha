package yong.app.domain.code.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.domain.code.group.YongGroupCode;
import yong.app.domain.code.group.YongGroupCodeDTO;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "yong_common_code")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongCommonCode extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_common_code_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yong_group_code_id")
    private YongGroupCode yongGroupCode;

    private String name;
    private String description;

    @Column(name = "delete_yn")
    private String deleteYn;

    @Builder(builderMethodName = "insertCommonCodeBuilder")
    public YongCommonCode(YongGroupCode yongGroupCode, String name, String description) {
        this.yongGroupCode = yongGroupCode;
        this.name = name;
        this.description = description;
        this.deleteYn = "N";
    }

    public void updateCommonCode(YongCommonCodeDTO yongCommonCodeDTO){
        if(yongCommonCodeDTO.getName() != null) this.name = yongCommonCodeDTO.getName();
        if(yongCommonCodeDTO.getDescription() != null) this.description = yongCommonCodeDTO.getDescription();
        if(yongCommonCodeDTO.getYongGroupCode() != null) this.yongGroupCode = yongCommonCodeDTO.getYongGroupCode();
    }

    public void deleteCommonCode(){
        this.deleteYn = "Y";
    }
}
