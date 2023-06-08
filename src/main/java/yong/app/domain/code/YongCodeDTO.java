package yong.app.domain.code;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class YongCodeDTO {

    private Long parentId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private YongCode parentCode;
}
