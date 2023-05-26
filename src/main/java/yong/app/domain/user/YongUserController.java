package yong.app.domain.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yong.app.domain.auth.RoleType;
import yong.app.domain.auth.YongUsersRole;
import yong.app.global.auth.PrincipalDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class YongUserController {
    private final YongUserService yongUserService;
    private final YongUserRepository repository;
    private final ModelMapper modelMapper;

    @PostMapping("/users/join")
    public ResponseEntity join(UserForm userForm) {

        return ResponseEntity.ok(yongUserService.join(userForm));
    }

    @PutMapping("/users/join/{id}")
    public ResponseEntity update(UserForm userForm) {
        yongUserService.update(userForm);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/users/join/add")
    public ResponseEntity addAuthor(UserForm userForm) {
        yongUserService.update(userForm);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/users/auth")
    public ResponseEntity auth(@AuthenticationPrincipal PrincipalDetails details) {
        log.info("details: {}", details);
        log.info("details: {}", details.getUser());
        log.info("details: {}", details.getAuthorities());
        return ResponseEntity.ok(details);
    }


    @GetMapping("/users/find")
    private ResponseEntity<YongUser> findByEmail(String email) {
        YongUser byEmail = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no user"));

        log.info(byEmail.getEmail());

        Set<YongUsersRole> yongRoles = byEmail.getYongRoles();
        for (YongUsersRole yongRole : yongRoles) {
            log.info("yongRole: {}", yongRole.getYongRole().getRoleType());
        }
        UserForm map = modelMapper.map(byEmail, UserForm.class);
        return ResponseEntity.ok().body(byEmail);
    }

    @GetMapping("/users/authti")
    private ResponseEntity getAuth(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("principalDetails: {}", principalDetails.getUser());
//        Set<YongUsersRole> yongRoles = principalDetails.getUser().getYongRoles();
//        for (YongUsersRole yongRole : yongRoles) {
//            log.info("yongRole: {}", yongRole);
//        }
        return ResponseEntity.ok().body(principalDetails);
    }
}
