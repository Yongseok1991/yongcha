package yong.app.domain.notification.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongNotificationController {

    private final YongNotificationService yongNotificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<YongNotificationVO>> list(){
        List<YongNotificationVO> list = yongNotificationService.list();
        return ResponseEntity.ok(list);
    }
}
