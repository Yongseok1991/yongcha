package yong.app.domain.menu;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @fileName Menu
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-05-01
 * @summary   Menu Entity
 **/

@Entity
@Getter
@NoArgsConstructor
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "메뉴 id", description = "메뉴 id", hidden = true)
    private Long id;

    @Schema(name = "메뉴 이름", description = "메뉴 이름")
    private String name;

    @Schema(name = "부모메뉴", description = "부모 메뉴")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Menu parent;

    @Schema(name = "깊이", description = "메뉴 깊이")
    private Long depth;

    @Schema(name = "부모메뉴", description = "부모 메뉴", hidden = true)
    @OneToMany(mappedBy = "parent")
    private List<Menu> children = new ArrayList<>();

    @Builder(builderMethodName = "toEntityForInsert")
    public Menu(String name, Menu parentMenu, Long depth) {
        this.name = name;
        this.parent = parentMenu;
        this.depth = depth;
    }

    public void update(String name, Menu parentMenu, Long depth){
        this.name = name;
        this.parent = parentMenu;
        this.depth = depth;
    }
}
