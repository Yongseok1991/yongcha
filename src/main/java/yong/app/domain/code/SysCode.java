package yong.app.domain.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import yong.app.global.base.BaseEntity;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @fileName SysCode
* @author dahyeon
* @version 1.0.0
* @date 2023-04-28
* @summary  Entity - getter(0) , setter(x)
 *          Schema 정의 -> swagger Schema 설명
 *          SysCodeBuilderInsert : sysCode insert
 *          update : sysCode update
**/

@Entity
@Getter
@NoArgsConstructor
public class SysCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "코드 ID", description = "sysCode의 id값")
    private Long id;            //코드 ID

    @Schema(name = "code값", description = "sysCode의 code값")
    private String code;        //코드

    @Schema(name = "상위코드", description = "sysCode의 상위코드값")
    private String upperCode;   //상위코드

    @Schema(name = "코드명", description = "sysCode의 코드명")
    private String codeNm;      //코드명

    @Schema(name = "코드설명", description = "sysCode의 코드설명")
    private String codeDc;      //코드 설명

    @Schema(name = "사용여부", description = "sysCode의 사용여부")
    private String useAt;      //사용 여부

    @Schema(name = "코드 레벨", description = "sysCode의 래벨")
    private Long lv;         //코드 레벨

    @Schema(name = "비고설명", description = "sysCode의 비고설명")
    private String rmDc;      //비고설명

    @Schema(name = "코드 정렬 순서", description = "sysCode의 정렬 번호")
    private Long sortNo;      //정렬순서번호

    @Builder(builderMethodName = "SysCodeBuilderInsert")
    public SysCode(String code, String upperCode, String codeNm, String codeDc, String useAt, Long lv, String rmDc, Long sortNo) {
        this.code = code;
        this.upperCode = upperCode;
        this.codeNm = codeNm;
        this.codeDc = codeDc;
        this.useAt = useAt;
        this.lv = lv;
        this.rmDc = rmDc;
        this.sortNo = sortNo;
    }

    public void update(String code, String upperCode, String codeNm, String codeDc, String useAt, Long lv, String rmDc, Long sortNo){
        this.code = code;
        this.upperCode = upperCode;
        this.codeNm = codeNm;
        this.codeDc = codeDc;
        this.useAt = useAt;
        this.lv = lv;
        this.rmDc = rmDc;
        this.sortNo = sortNo;
    }

}
