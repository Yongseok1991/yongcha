package yong.app.domain.code.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongCommonCodeController {

    private final YongCommonCodeService yongCommonCodeService;

    @GetMapping("/common/codes")
    public ResponseEntity<List<YongCommonCodeVO>> list(){
        List<YongCommonCodeVO> list = yongCommonCodeService.list();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/common/codes")
    public ResponseEntity<Long> insert(@RequestBody YongCommonCodeDTO yongCommonCodeDTO){
        Long joinId = yongCommonCodeService.join(yongCommonCodeDTO);
        return ResponseEntity.ok(joinId);
    }

    @GetMapping("/common/codes/{id}")
    public ResponseEntity<YongCommonCodeVO> show(@PathVariable("id") Long id){
        YongCommonCodeVO show = yongCommonCodeService.show(id);
        return ResponseEntity.ok(show);
    }

    @PutMapping("/common/codes/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongCommonCodeDTO yongCommonCodeDTO){
        yongCommonCodeService.update(id, yongCommonCodeDTO);
        return ResponseEntity.ok("updated!!");
    }

    @DeleteMapping("/common/codes/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongCommonCodeService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }

}
