package yong.app.domain.menu;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @fileName MenuResult
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-05-01
 * @summary   Menu 출력
 **/

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResult {

    private Long id;
    private String name;
    private Long depth;
    private List<MenuResult> children;

    public static MenuResult of(Menu menu) {
        return new MenuResult(
                menu.getId(),
                menu.getName(),
                menu.getDepth(),
                menu.getChildren().stream().map(MenuResult::of).collect(Collectors.toList())
        );
    }
}
