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
@AllArgsConstructor // 모든 필드 값을 받는 생성자 생성
@NoArgsConstructor  // 파라미터가 없는 기본 생성자 생성
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
                menu.getChildren().stream().map(MenuResult::of).collect(Collectors.toList())      // TODO : 분석
        );
    }

    public Boolean hasChildren(){
         return !this.children.isEmpty();
    }
}
