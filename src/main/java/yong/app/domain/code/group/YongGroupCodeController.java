package yong.app.domain.code.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class YongGroupCodeController {

    private final YongGroupCodeService yongGroupCodeService;

    @GetMapping("/group/codes")
    public ResponseEntity<List<YongGroupCodeVO>> list(){
        List<YongGroupCodeVO> list = yongGroupCodeService.list();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/group/codes")
    public ResponseEntity<Long> insert(@RequestBody YongGroupCodeDTO yongGroupCodeDTO){
        Long joinId = yongGroupCodeService.join(yongGroupCodeDTO);
        return ResponseEntity.ok(joinId);
    }

    @GetMapping("/group/codes/{id}")
    public ResponseEntity<YongGroupCodeVO> show(@PathVariable("id") Long id){
        YongGroupCodeVO show = yongGroupCodeService.show(id);
        return ResponseEntity.ok(show);
    }

    @PutMapping("/group/codes/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongGroupCodeDTO yongGroupCodeDTO){
        yongGroupCodeService.update(id, yongGroupCodeDTO);
        return ResponseEntity.ok("updated!!");
    }

    @DeleteMapping("/group/codes/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongGroupCodeService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }
}

