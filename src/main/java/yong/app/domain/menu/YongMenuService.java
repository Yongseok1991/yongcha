package yong.app.domain.menu;

import yong.app.global.response.RestApiException;
import yong.app.global.response.StatusCode;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface YongMenuService {
    Menu save(MenuDTO menuDTO);
    List<MenuResult> getMenuList();
    List<MenuResult> getMenuListById(Long id);
    List<MenuResult> updateById(Long id, MenuDTO menuDTO);

}
