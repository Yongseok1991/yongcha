package yong.app.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
public class YongCodeVO {

    private Long id;
    private String name;
    private String description;
    private String deleteYn;
    private List<YongCodeVO> child = new ArrayList<>();


    public static YongCodeVO of(YongCode yongCode) {
        return new YongCodeVO(
                yongCode.getId(),
                yongCode.getName(),
                yongCode.getDescription(),
                yongCode.getDeleteYn(),
                yongCode.getChild().stream().map(YongCodeVO::of).toList()
        );
    }

}
