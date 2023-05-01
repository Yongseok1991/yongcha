package yong.app.domain.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @fileName SysCodeDTO
* @author dahyeon
* @version 1.0.0
* @date 2023-04-28
* @summary  DTO : getter(0), setter(0)
 *          Schema : name은 column명과 통일 (value : key로 보낼때, name이 value 자체로 들어감)
 *          toEntityForInsert : sysCode insert
**/

@Getter
@Setter
@NoArgsConstructor
public class SysCodeDTO  {

    // has getter, setter method 
    // dto, entity 분리 이유 : db, view 사이 역할 분리

    @Schema(name = "id", description = "sysCode의 id값", hidden = true)
    private Long id;            //코드 ID

    @Schema(name = "code", description = "sysCode의 code값", defaultValue = "", example = "testCd")
    private String code;        //코드

    @Schema(name = "upperCode", description = "sysCode의 상위코드값", defaultValue = "", example = "testCd")
    private String upperCode;   //상위코드

    @Schema(name = "codeNm", description = "sysCode의 코드명", defaultValue = "", example = "testCd")
    private String codeNm;      //코드명

    @Schema(name = "codeDc", description = "sysCode의 코드설명", defaultValue = "", example = "testCd")
    private String codeDc;      //코드 설명

    @Schema(name = "useAt", description = "sysCode의 사용여부", defaultValue = "Y")
    @NotBlank
    private String useAt;      //사용 여부

    @Schema(name = "lv", description = "sysCode의 래벨", defaultValue = "1")
    @NotNull
    private Long lv;         //코드 레벨

    @Schema(name = "rmDc", description = "sysCode의 비고설명", defaultValue = "", example = "비고")
    private String rmDc;      //비고설명

    @Schema(name = "sortNo", description = "sysCode의 정렬 번호", defaultValue = "1")
    private Long sortNo;      //정렬순서번호


    public SysCode toEntityForInsert(String code, String upperCode, String codeNm, String codeDc, String useAt, Long lv, String rmDc, Long sortNo) {
        return SysCode.SysCodeBuilderInsert()
                .code(code)
                .upperCode(upperCode)
                .codeNm(codeNm)
                .codeDc(codeDc)
                .useAt(useAt)
                .lv(lv)
                .rmDc(rmDc)
                .sortNo(sortNo)
                .build();
    }

}
