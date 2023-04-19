package yong.app.visa.user;

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
import java.util.HashSet;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final YongUserRepository yongUserRepository;

    @GetMapping("/admin")
    public String admin() {
        return "어드민 페이지입니다.";
    }

    //@PostAuthorize("hasRole('ROLE_MANAGER')")
    //@PreAuthorize("hasRole('ROLE_MANAGER')")
    @Secured("ROLE_MANAGER")
    @GetMapping("/manager")
    public String manager() {

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

    @GetMapping("/userList")
    public ResponseEntity users() {
        return ResponseEntity.ok(yongUserRepository.findAll());
    }
}
