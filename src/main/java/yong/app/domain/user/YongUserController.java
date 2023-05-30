package yong.app.domain.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yong.app.domain.auth.YongUsersRole;
import yong.app.global.auth.PrincipalDetails;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class YongUserController {
    private final YongUserService yongUserService;
    private final YongUserRepository repository;
    private final ModelMapper modelMapper;


    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/users") // get users info list
    public ResponseEntity<List<YongUserVO>> list(){
        List<YongUserVO> list = yongUserService.list();
        return ResponseEntity.ok(list);
    }


    // GET ONE
    // - 리턴 : vo
    @GetMapping("/users/{id}") // get user info by id
    public ResponseEntity<YongUserVO> show(@PathVariable("id") Long id){
        YongUserVO show = yongUserService.show(id);
        return ResponseEntity.ok(show);
    }

    @GetMapping("/login/users")  // get login user info
    public ResponseEntity<YongUserVO> showLoginUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        YongUserVO map = modelMapper.map(principalDetails.getUser(), YongUserVO.class);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/users/join")
    public ResponseEntity<Long> join(UserForm userForm) {
        Long joinId = yongUserService.join(userForm);
        return ResponseEntity.ok(joinId);
    }

    @PutMapping("/users/join/{id}")
    public ResponseEntity<String> update(UserForm userForm) {
        yongUserService.update(userForm);
        return ResponseEntity.ok("updated!!");
    }

    @PutMapping("/users/join/add")
    public ResponseEntity addAuthor(UserForm userForm) {
        yongUserService.update(userForm);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/users/find")
    private ResponseEntity<YongUser> findByEmail(String email) {
        YongUser byEmail = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no user"));

        log.info(byEmail.getEmail());

//        Set<YongUsersRole> yongRoles = byEmail.getYongRoles();
//        for (YongUsersRole yongRole : yongRoles) {
//            log.info("yongRole: {}", yongRole.getYongRole().getRoleType());
//        }
        UserForm map = modelMapper.map(byEmail, UserForm.class);
        return ResponseEntity.ok().body(byEmail);
    }

    @GetMapping("/test/users")
    private ResponseEntity test(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return  ResponseEntity.ok(principalDetails);
    }
}
