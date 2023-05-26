package yong.app.domain.file;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongFileController {

    private final YongFileService yongFileService;

    @GetMapping("/files")
    public ResponseEntity<List<YongFileVO>> list(){
        List<YongFileVO> findFiles = yongFileService.findAll();
        return  ResponseEntity.ok(findFiles);
    }

    @PostMapping("/files")
    private ResponseEntity<Long> insert(@RequestBody YongFileDTO yongFileDTO){

        return ResponseEntity.ok(null);
    }
}
