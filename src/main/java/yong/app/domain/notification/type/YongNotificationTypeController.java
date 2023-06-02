package yong.app.domain.notification.type;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yong.app.domain.notification.notification.YongNotification;

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
    public ResponseEntity<List<YongNotificationTypeVO>> list(){
        List<YongNotificationTypeVO> list = yongNotificationTypeService.list();
        return ResponseEntity.ok(list);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용
    @PostMapping("/notification/types")
    public ResponseEntity<Long> insert(@RequestBody YongNotificationTypeDTO yongNotificationTypeDTO){
        Long joinId = yongNotificationTypeService.join(yongNotificationTypeDTO);
        return ResponseEntity.ok(joinId);
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경 메서드 이용 -> update
    @PutMapping("/notification/types/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongNotificationTypeDTO yongNotificationTypeDTO){
        yongNotificationTypeService.update(id, yongNotificationTypeDTO);
        return ResponseEntity.ok("updated!!");
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/notification/types/{id}")
    public ResponseEntity<YongNotificationTypeVO> show(@PathVariable("id") Long id){
        YongNotificationTypeVO show = yongNotificationTypeService.show(id);
        return ResponseEntity.ok(show);
    }
}
