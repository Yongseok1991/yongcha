package yong.app.domain.region;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yong.app.global.auth.PrincipalDetails;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "region 컨트롤러", description = "지역 컨트롤러")
public class YongRegionController {

    private final YongRegionService yongRegionService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @Operation(summary = "show region list", description = "전체 지역 리스트 조회")
    @ApiDocumentResponse
    @GetMapping("/regions")  // get user region list
    public StatusResponse list(){
        List<YongRegionVO> list = yongRegionService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 지역 리스트 반환");
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 :  (1) 로그인 유저의 email을 통해 지역 row 받기
    //          (2) if : (1) 개수 2개 -> exception
    //          (3) else : builder을 통해 save
    @Operation(summary = "insert region", description = "지역 삽입")
    @ApiDocumentResponse
    @PostMapping("/regions") // insert user region by login info
    public StatusResponse joinByLoginEmail(@RequestBody YongRegionDTO yongRegionDTO, @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        Long joinId = yongRegionService.joinByLoginEmail(email, yongRegionDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "유저 지역 생성 성공");
    }

    // GET ONE
    // - 리턴 : vo list
    // - 방법 : 로그인 유저의 email을 통해 지역 row list 받기 (최대 2개)
    @Operation(summary = "show user region view", description = "유저 지역 리스트 조회")
    @ApiDocumentResponse
    @GetMapping("/user/regions") // get user region by login info
    public StatusResponse showByLoginEmail(@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        List<YongRegionVO> yongRegionVOS = yongRegionService.showByLoginEmail(email);
        if(yongRegionVOS.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, yongRegionVOS, "유저의 지역 리스트 반환");
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : (1) 로그인 유저의 email & 지역id를 통해 찾기
    //         (2) 변경 메서드를 통해 update
    @Operation(summary = "update region", description = "지역 업데이트")
    @ApiDocumentResponse
    @PutMapping("/user/regions/{id}") // update user region by login info and region id
    public StatusResponse updateByLoginEmail(@PathVariable("id") Long id, @RequestBody YongRegionDTO yongRegionDTO ,@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        yongRegionService.updateByLoginEmail(id, email, yongRegionDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : (1) 로그인 유저의 email & 지역id를 통해 찾기
    //         (2) 삭제
    @Operation(summary = "delete region", description = "지역 삭제")
    @ApiDocumentResponse
    @DeleteMapping("/user/regions/{id}")
    public StatusResponse deleteByLoginEmail(@PathVariable("id") Long id, @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        yongRegionService.deleteByLoginEmail(id, email);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
