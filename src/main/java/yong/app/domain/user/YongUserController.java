package yong.app.domain.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yong.app.domain.token.YongConfirmTokenService;
import yong.app.global.auth.PrincipalDetails;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "user 컨트롤러", description = "유저 컨트롤러")
@Slf4j
public class YongUserController {
    private final YongUserService yongUserService;
    private final YongUserRepository repository;
    private final YongConfirmTokenService yongConfirmTokenService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @Operation(summary = "show user list", description = "전체 유저 리스트 조회")
    @ApiDocumentResponse
    @GetMapping("/users") // get users info list
    public StatusResponse list(){
        List<YongUserVO> list = yongUserService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 유저 리스트 조회");
    }

    // GET ONE by ID
    // - 리턴 : vo
    // - 방법 : findById -> 모델매펄르 통해 vo로 변경
    @Operation(summary = "show user view by id", description = "유저 조회 by id")
    @ApiDocumentResponse
    @GetMapping("/users/{id}") // get user info by id
    public StatusResponse show(@PathVariable("id") Long id){
        YongUserVO show = yongUserService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "유저 단건 조회");
    }

    // GET ONE by LOGIN EMAIL
    // - 리턴 : vo
    // - 방법 : principalDetails에서 user가져와 모델매퍼를 통해 vo로 변경
    @Operation(summary = "show user view by login info", description = "유저 조회 by login info")
    @ApiDocumentResponse
    @GetMapping("/login/users")  // get login user info
    public StatusResponse showLoginUser(@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principalDetails){
//        YongUserRecord map = modelMapper.map(principalDetails.getUser(), YongUserRecord.class);
        YongUserVO yongUserVO = new YongUserVO(principalDetails.getUser());
        return new StatusResponse(StatusCode.SUCCESS, yongUserVO, "로그인한 유저 단건 조회");
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : (1) findAllByRoleTypeIn -> role type get
    //         (2) builder 이용
    //         (3) 1 user의 builder에서 각각의 user-role에 대해 builder 생성
    //         (4) save (cascade : all)
    @Operation(summary = "insert user", description = "유저 삽입(회원가입)")
    @ApiDocumentResponse
    @PostMapping("/users/join") // insert info
    public StatusResponse join(@RequestBody YongUserDTO yongUserDTO) {
        Long joinId = yongUserService.join(yongUserDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "유저 생성");
    }

    // UPDATE by ID
    // - 리턴 : void
    // - 방법 : (1) id로 user find
    //         (2) role type이 이미 user가 가지고 있는지 유무 판단
    //         (3) if : 가지고 있다면 exception
    //         (4) else : 변경 메서드를 통해 update (추가되는 권한에 대해서는 builder을 통해 매핑 테이릅에도 추가)
    @Operation(summary = "update user by id", description = "유저 업데이트 by id")
    @ApiDocumentResponse
    @PutMapping("/users/join/{id}") // update user by id
    public StatusResponse updateById(@PathVariable Long id, @RequestBody YongUserDTO yongUserDTO) {
        yongUserService.updateById(id, yongUserDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    // UPDATE by Login Email
    // - 리턴 : void
    // - 방법 : (1) principalDetails에서 user email 가져오기
    //         (2) role type이 이미 user가 가지고 있는지 유무 판단
    //         (3) if : 가지고 있다면 exception
    //         (4) else : 변경 메서드를 통해 update (추가되는 권한에 대해서는 builder을 통해 매핑 테이릅에도 추가)
    @Operation(summary = "update user by login info", description = "유저 업데이트 by login info")
    @ApiDocumentResponse
    @PutMapping("/users/login/join") // update user by login info
    public StatusResponse updateByLoginEmail(@RequestBody YongUserDTO yongUserDTO, @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String email = principalDetails.getUser().getEmail();
        System.out.println("email = " + email);
        yongUserService.updateByLoginEmail(email, yongUserDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    @Operation(summary = "update user's author", description = "유저의 권한 업데이트")
    @ApiDocumentResponse
    @PutMapping("/users/join/add")
    public StatusResponse addAuthor(@RequestBody YongUserDTO yongUserDTO) {
        yongUserService.update(yongUserDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    @Operation(summary = "find user by email", description = "이메일로 유저 찾기")
    @ApiDocumentResponse
    @GetMapping("/users/find")
    private StatusResponse findByEmail(String email) {
        YongUser byEmail = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no user"));
        log.info(byEmail.getEmail());

        return new StatusResponse(StatusCode.SUCCESS, byEmail, "로그인한 유저 정보 조회");
    }
    
    @Operation(summary = "token을 통해 user confirm 등록", description = "token을 통해 user confirm 등록")
    @ApiDocumentResponse
    @GetMapping(path = "/users/confirm-token")
    public StatusResponse confirm(String token) {
        yongUserService.confirmToken(token);
        return new StatusResponse(StatusCode.SUCCESS, "confirmed");
    }
}
