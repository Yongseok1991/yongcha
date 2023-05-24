package yong.app.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yong.app.global.auth.PrincipalDetails;

import javax.validation.Valid;

;

/**
 * @fileName UserRestController
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-04-26
 * @summary swagger에 대한 예시 작성
 *         ApiResponse : Error에 대해 ResponseBody 반환 설정 필요 있을 듯.
 **/

@Slf4j
@Tag(name="유저 컨트롤러", description="유저 컨트롤러 관련 api입니다.")              // ** 같은 Tag name에 대해서는 같은 api 그룹으로 인식
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class YongUserRestController {
    private final YongUserService yongUserService;

    @Operation(summary = "show admin page", description = "admin page를 보여줍니다.")   // ** api 동작에 대한 명세를 적는 어노테이션
    // ** HTTP 상태 코드에 대해 반환 정보 설정
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = YongUser.class))),       // YongUser class가 ResponseBody에 반환
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class))),       // Error class가 ResponseBody에 반환 (후에 커스터마이징을 통해 error 반환 바꾸기)
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)
    })
    // ** parameters
    @Parameters({
            @Parameter(name = "uid", description = "유저의 uid", required = true),
            @Parameter(name = "username", description = "유저의 name", required = false),
            @Parameter(name = "password", description = "유저의 password", required = false)
    })
    @GetMapping("/admin")
    public ResponseEntity admin() {
        return ResponseEntity.ok(yongUserService.findAll());
    }

    //@PostAuthorize("hasRole('ROLE_MANAGER')")
    //@PreAuthorize("hasRole('ROLE_MANAGER')")
    @Secured("ROLE_MANAGER")
    @GetMapping("/manager")
    public  String manager() {
        return "매니저 페이지입니다.";
    }

    @PostMapping("/joinProc")
    public ResponseEntity joinProc(@Valid YongUserDTO user) {
        return ResponseEntity.ok(yongUserService.joinProc(YongAuthor.ROLE_USER, user));
    }

    @GetMapping("/tests")
    public ResponseEntity tests(@AuthenticationPrincipal PrincipalDetails principalDetails){

        log.info("{} -> ", principalDetails.getUser());
        log.info("{} -> ", principalDetails.getAttributes());

        return null;
    }

    @GetMapping("/users/{username}/exists")
    public ResponseEntity checkUsernameDuplicate(@PathVariable String username) {
        return ResponseEntity.ok(yongUserService.checkUsernameDuplicate(username));
    }

}
