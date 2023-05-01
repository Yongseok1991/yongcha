package yong.app.domain.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yong.app.global.response.RestApiException;
import yong.app.global.response.StatusCode;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @fileName MenuService
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-05-01
 * @summary   Menu Service
 *            - insert : parent 있음 없음에 따라 다르게 insert
 *            - update : parent 있음 없음에 따라 다르게 update
 **/

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu save(MenuDTO menuDTO){

        Optional<Menu> parent = menuRepository.findById(menuDTO.getParentId());
        Menu parentMenu = (!parent.isEmpty()) ? parent.get() : null;
        Menu menu = new MenuDTO().toEntityForInsert(menuDTO.getName(), parentMenu, menuDTO.getDepth());

        menuRepository.save(menu);
        return menu;
    }

    @Transactional
    public List<MenuResult> getMenuList(){
        // findAllByParentIsNullOrderById : 계층형 출력 + id로 order
        // -> findAll()로 하게 되면, 계층형 + 전체 data 출력
        List<Menu> results = menuRepository.findAllByParentIsNullOrderById();
        return results.stream().map(MenuResult::of).collect(Collectors.toList());
    }

    @Transactional
    public List<MenuResult> getMenuListById(Long id){
        List<MenuResult> results = menuRepository.findById(id).stream().map(MenuResult::of).collect(Collectors.toList());
        return results;
    }

    @Transactional
    public List<MenuResult> updateById(Long id, MenuDTO menuDTO){
        Optional <Menu> menu = menuRepository.findById(id);

        // 해당 ID가 있는지 여부 check
        if(!menu.isPresent()){
            menu.orElseThrow(() -> new RestApiException(StatusCode.BAD_REQUEST, "PutMapping Error"));
        }else{
            
            Optional<Menu> parent = menuRepository.findById(menuDTO.getParentId());

            Menu parentMenu = (!parent.isEmpty()) ? parent.get() : null;
            menu.get().update(menuDTO.getName(), parentMenu, menuDTO.getDepth());
        }

        return menuRepository.findById(id).stream().map(MenuResult::of).collect(Collectors.toList());
    }
}
