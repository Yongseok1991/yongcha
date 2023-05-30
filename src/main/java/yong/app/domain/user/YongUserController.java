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

    // GET ONE by ID
    // - 리턴 : vo
    // - 방법 : findById -> 모델매펄르 통해 vo로 변경
    @GetMapping("/users/{id}") // get user info by id
    public ResponseEntity<YongUserVO> show(@PathVariable("id") Long id){
        YongUserVO show = yongUserService.show(id);
        return ResponseEntity.ok(show);
    }

    // GET ONE by LOGIN EMAIL
    // - 리턴 : vo
    // - 방법 : principalDetails에서 user가져와 모델매퍼를 통해 vo로 변경
    @GetMapping("/login/users")  // get login user info
    public ResponseEntity<YongUserVO> showLoginUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        YongUserVO map = modelMapper.map(principalDetails.getUser(), YongUserVO.class);
        return ResponseEntity.ok(map);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : (1) findAllByRoleTypeIn -> role type get
    //         (2) builder 이용
    //         (3) 1 user의 builder에서 각각의 user-role에 대해 builder 생성
    //         (4) save (cascade : all)
    @PostMapping("/users/join") // insert info
    public ResponseEntity<Long> join(@RequestBody YongUserDTO yongUserDTO) {
        Long joinId = yongUserService.join(yongUserDTO);
        return ResponseEntity.ok(joinId);
    }

    // UPDATE by ID
    // - 리턴 : void
    // - 방법 : (1) id로 user find
    //         (2) role type이 이미 user가 가지고 있는지 유무 판단
    //         (3) if : 가지고 있다면 exception
    //         (4) else : 변경 메서드를 통해 update (추가되는 권한에 대해서는 builder을 통해 매핑 테이릅에도 추가)
    @PutMapping("/users/join/{id}") // update user by id
    public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody YongUserDTO yongUserDTO) {
        yongUserService.updateById(id, yongUserDTO);
        return ResponseEntity.ok("updated by id!!");
    }

    // UPDATE by Login Email
    // - 리턴 : void
    // - 방법 : (1) principalDetails에서 user email 가져오기
    //         (2) role type이 이미 user가 가지고 있는지 유무 판단
    //         (3) if : 가지고 있다면 exception
    //         (4) else : 변경 메서드를 통해 update (추가되는 권한에 대해서는 builder을 통해 매핑 테이릅에도 추가)
    @PutMapping("/users/login/join") // update user by login info
    public ResponseEntity<String> updateByLoginEmail(@RequestBody YongUserDTO yongUserDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String email = principalDetails.getUser().getEmail();
        System.out.println("email = " + email);
        yongUserService.updateByLoginEmail(email, yongUserDTO);
        return ResponseEntity.ok("updated by email!!");
    }

    @PutMapping("/users/join/add")
    public ResponseEntity addAuthor(@RequestBody YongUserDTO yongUserDTO) {
        yongUserService.update(yongUserDTO);
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
}
