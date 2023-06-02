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

    // GET LIST
    // - 리턴 : vo
    // - 방법 : findAll() -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/group/codes")
    public ResponseEntity<List<YongGroupCodeVO>> list(){
        List<YongGroupCodeVO> list = yongGroupCodeService.list();
        return ResponseEntity.ok(list);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용
    @PostMapping("/group/codes")
    public ResponseEntity<Long> insert(@RequestBody YongGroupCodeDTO yongGroupCodeDTO){
        Long joinId = yongGroupCodeService.join(yongGroupCodeDTO);
        return ResponseEntity.ok(joinId);
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/group/codes/{id}")
    public ResponseEntity<YongGroupCodeVO> show(@PathVariable("id") Long id){
        YongGroupCodeVO show = yongGroupCodeService.show(id);
        return ResponseEntity.ok(show);
    }
    
    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경 메서드 이용 -> update
    @PutMapping("/group/codes/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongGroupCodeDTO yongGroupCodeDTO){
        yongGroupCodeService.update(id, yongGroupCodeDTO);
        return ResponseEntity.ok("updated!!");
    }

    // DELETE
    // - 리턴 : void
    // - 방법 :   (if)  해당 그룹 코드의 자식코드(common-code)가 있을 경우 삭제 불가능
    //          (else) delete_yn 컬럼 'Y'로 변경
    @DeleteMapping("/group/codes/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongGroupCodeService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }
}

