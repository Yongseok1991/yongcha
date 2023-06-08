package yong.app.domain.notification.notification;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import yong.app.global.auth.PrincipalDetails;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class YongNotificationController {

    private final YongNotificationService yongNotificationService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/notifications")
    public StatusResponse list(){
        List<YongNotificationVO> list = yongNotificationService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 알림 리스트 조회");
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용
    @PostMapping("/notifications")
    public StatusResponse insert(@RequestBody YongNotificationDTO yongNotificationDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        Long joinId = yongNotificationService.join(email, yongNotificationDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "알림 생성 성공");
    }

    // GET ONE by ID
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/notifications/{id}")
    public StatusResponse showById(@PathVariable("id") Long id){
        YongNotificationVO yongNotificationVO = yongNotificationService.showById(id);
        return new StatusResponse(StatusCode.SUCCESS, yongNotificationVO, "알림 단일건 조회");
    }

    // GET ONE by USER's email
    // - 리턴 : vo list
    // - 방법 : findByYongUserEmail -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/notifications/user")
    public StatusResponse showUserNotify(@AuthenticationPrincipal PrincipalDetails principalDetails){
        String email = principalDetails.getUser().getEmail();
        List<YongNotificationVO> yongNotificationVOS = yongNotificationService.showUserNotify(email);
        if(yongNotificationVOS.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, yongNotificationVOS, "유저의 알림 조회");
    }

    // UPDATE
    // - 리턴 : void
    // - 방법   (1) notification type is null -> exception
    //         (2) notification is not founded -> exception
    //         (3) 변경 메서드를 통해 update 처리
    //         ** 'user'의 알림이기 때문에 -> user 필드는 update x
    @PutMapping("/notifications/{id}")
    public StatusResponse updateById(@PathVariable("id") Long id, @RequestBody YongNotificationDTO yongNotificationDTO){
        yongNotificationService.updateById(id, yongNotificationDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete
    @DeleteMapping("/notifications/{id}")
    public StatusResponse deleteById(@PathVariable("id") Long id){
        yongNotificationService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
