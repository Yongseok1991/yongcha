package yong.app.domain.notification.type;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.RestApiException;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongNotificationTypeController {

    private final YongNotificationTypeService yongNotificationTypeService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/notification/types")
    public StatusResponse list(){
        List<YongNotificationTypeVO> list = yongNotificationTypeService.list();
        if(list.isEmpty()) throw new RestApiException(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "전체 알림 타입 조회");
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용
    @PostMapping("/notification/types")
    public StatusResponse insert(@RequestBody YongNotificationTypeDTO yongNotificationTypeDTO){
        Long joinId = yongNotificationTypeService.join(yongNotificationTypeDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "알림 타입 생성 성공");
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경 메서드 이용 -> update
    @PutMapping("/notification/types/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongNotificationTypeDTO yongNotificationTypeDTO){
        yongNotificationTypeService.update(id, yongNotificationTypeDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/notification/types/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongNotificationTypeVO show = yongNotificationTypeService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "알림 타입 단일 조회");
    }
}
