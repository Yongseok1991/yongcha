package yong.app.domain.user;


import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import yong.app.domain.auth.RoleType;
import yong.app.domain.auth.YongRoleRepository;
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

    @GetMapping("/users/auth")
    public ResponseEntity auth(@AuthenticationPrincipal PrincipalDetails details) {
        log.info("details: {}", details);
        log.info("details: {}", details.getUser());
        log.info("details: {}", details.getAuthorities());
        return ResponseEntity.ok(null);
    }


    @GetMapping("/users/find")
    private ResponseEntity findByEmail(String email) {
        Optional<YongUser> byEmail = repository.findByEmail(email);
        modelMapper.addMappings(memberToMemberVO(email));
        UserForm map = modelMapper.map(byEmail.get(), UserForm.class);

        return ResponseEntity.ok().body(byEmail);
    }


    public PropertyMap<YongUser, UserForm> memberToMemberVO(String email) {
        List<RoleType> roleTypes = new ArrayList<>();
        List<RoleType> roleTypeByEmail = yongUserService.findRoleTypeByEmail(email);
        PropertyMap<YongUser, UserForm> replyMapping = new PropertyMap<>() {
            @Override
            protected void configure() {    // 매핑 정보 입력
                map().setEmail(source.getEmail());

                // source -> get RoleTypes -> add to list
                map().setRoleType(roleTypeByEmail);
            }
        };
        return replyMapping;
    }
=======
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yong.app.domain.role.YongRoleRepository;

@Controller
@RequiredArgsConstructor
public class YongUserController {

    private final YongRoleRepository yongRoleRepository;
    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("author", yongRoleRepository.findAll());
        model.addAttribute("user", new YongUser());
        return "app/join/index";
    }

>>>>>>> 0e1214fc03bcd69231a9b91c0e081f0ff33e16c5
}
