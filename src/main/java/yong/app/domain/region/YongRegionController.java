package yong.app.domain.region;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yong.app.global.auth.PrincipalDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongRegionController {

    private final YongRegionService yongRegionService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/regions")  // get user region list
    public ResponseEntity<List<YongRegionVO>> list(){
        List<YongRegionVO> list = yongRegionService.list();
        return ResponseEntity.ok(list);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 :  (1) 로그인 유저의 email을 통해 지역 row 받기
    //          (2) if : (1) 개수 2개 -> exception
    //          (3) else : builder을 통해 save
    @PostMapping("/regions") // insert user region by login info
    public ResponseEntity<Long> joinByLoginEmail(@RequestBody YongRegionDTO yongRegionDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        Long joinId = yongRegionService.joinByLoginEmail(email, yongRegionDTO);
        return ResponseEntity.ok(joinId);
    }

    // GET ONE
    // - 리턴 : vo list
    // - 방법 : 로그인 유저의 email을 통해 지역 row list 받기 (최대 2개)
    @GetMapping("/user/regions") // get user region by login info
    public ResponseEntity<List<YongRegionVO>> showByLoginEmail(@AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        List<YongRegionVO> yongRegionVOS = yongRegionService.showByLoginEmail(email);
        return ResponseEntity.ok(yongRegionVOS);
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : (1) 로그인 유저의 email & 지역id를 통해 찾기
    //         (2) 변경 메서드를 통해 update
    @PutMapping("/user/regions/{id}") // update user region by login info and region id
    public ResponseEntity<String> updateByLoginEmail(@PathVariable("id") Long id, @RequestBody YongRegionDTO yongRegionDTO ,@AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        yongRegionService.updateByLoginEmail(id, email, yongRegionDTO);
        return ResponseEntity.ok("updated!!");
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : (1) 로그인 유저의 email & 지역id를 통해 찾기
    //         (2) 삭제
    @DeleteMapping("/user/regions/{id}")
    public ResponseEntity<String> deleteByLoginEmail(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        yongRegionService.deleteByLoginEmail(id, email);
        return ResponseEntity.ok("deleted!!");
    }
}
