package yong.app.visa.user;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import yong.app.visa.role.Role;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final YongUserRepository yongUserRepository;

    @Operation(summary = "show admin page", description = "show admin page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = YongUser.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @Parameters({
            @Parameter(name = "id", description = "id", required = true)
    })
    @GetMapping("/admin")
    public String admin() {
        return "어드민 페이지입니다.";
    }

    //@PostAuthorize("hasRole('ROLE_MANAGER')")
    //@PreAuthorize("hasRole('ROLE_MANAGER')")
    @Secured("ROLE_MANAGER")
    @GetMapping("/manager")
    public  String manager() {
        return "매니저 페이지입니다.";
    }

    @PostMapping("/joinProc")
    public ResponseEntity joinProc(@Valid YongUser user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        Role role = new Role();
        role.setRoleName("ROLE_MANAGER");
        user.setRoles(new ArrayList<>(Arrays.asList(role)));
        return ResponseEntity.ok(yongUserRepository.save(user));
    }
}
