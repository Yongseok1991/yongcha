package yong.app.domain.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import yong.app.domain.code.SysCode;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.RestApiException;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import javax.persistence.Table;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "menu 컨트롤러", description="menu 컨트롤러입니다.")
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;
    private final MenuRepository menuRepository;

    @Operation(summary = "show menu list", description = "show menu list")
    @Transactional(readOnly = true)
    @ApiDocumentResponse
    @GetMapping("/menus")
    public ResponseEntity getMenus(){
        List<MenuResult> menus = menuService.getMenuList();

        if(menus == null){
            throw new RestApiException(StatusCode.BAD_REQUEST, "there is no list");
        }

        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS.getHttpStatus().value(), StatusCode.SUCCESS.name(), StatusCode.SUCCESS.getMessage(), menus));
    }

    @Operation(summary = "insert menu list", description = "insert menu list")
    @ApiDocumentResponse
    @PostMapping("/menus")
    public ResponseEntity insert(@RequestBody @Valid MenuDTO menuDTO){
        menuService.save(menuDTO);
        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS.getHttpStatus().value(), StatusCode.SUCCESS.name(), StatusCode.SUCCESS.getMessage(), menuDTO));
    }

    @Operation(summary = "view menu by id", description = "view menu by id")
    @Transactional(readOnly = true)
    @ApiDocumentResponse
    @GetMapping("/menus/{id}")
    public ResponseEntity view(@PathVariable("id") Long id){
        Optional<Menu> optional = menuRepository.findById(id);

        if(!optional.isPresent()) {
            optional.orElseThrow(() -> new RestApiException(StatusCode.BAD_REQUEST, "GetMapping Error"));
        }

        List<MenuResult> menus = menuService.getMenuListById(id);
        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS.getHttpStatus().value(), StatusCode.SUCCESS.name(), StatusCode.SUCCESS.getMessage(), menus));
    }


    @Operation(summary = "update menu by id", description = "update menu by id")
    @ApiDocumentResponse
    @PutMapping("/menus/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid MenuDTO menuDTO){
        List<MenuResult> menus = menuService.updateById(id, menuDTO);

        return ResponseEntity
                .status(StatusCode.SUCCESS.getHttpStatus().value())
                .body(new StatusResponse(StatusCode.SUCCESS.getHttpStatus().value(), StatusCode.SUCCESS.name(), StatusCode.SUCCESS.getMessage(), menus));

    }


}

