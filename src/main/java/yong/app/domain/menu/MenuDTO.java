package yong.app.domain.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @fileName MenuDTO
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-05-01
 * @summary   MenuDTO
 *            - parentId : child일 경우, 해당 parent Id값
 **/


@Getter
@Setter
@NoArgsConstructor
public class MenuDTO {


    @Schema(name = "id", description = "메뉴 id", hidden = true)
    private Long id;

    @Schema(name = "name", description = "메뉴 이름")
    @NotBlank
    private String name;

    @Schema(name = "parentId", description = "부모 메뉴")
    private Long parentId;

    @Schema(name = "depth", description = "메뉴 깊이")
    @NotNull
    private Long depth;

    @Schema(name = "children", description = "부모 메뉴", hidden = true)
    private List<Menu> children = new ArrayList<>();

    public Menu toEntityForInsert(String name, Menu parentMenu, Long depth) {
        return Menu.toEntityForInsert()
                .name(name)
                .parentMenu(parentMenu)
                .depth(depth)
                .build();
    }

}
