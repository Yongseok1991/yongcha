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

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/common/codes")
    public ResponseEntity<List<YongCommonCodeVO>> list(){
        List<YongCommonCodeVO> list = yongCommonCodeService.list();
        return ResponseEntity.ok(list);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : ** common-code의 경우, 필수적으로 parent code(group-code) 가 필요
    //         (1) group-code is null -> exception
    //         (2) group-code -> findByIdAndDeleteYnIs -> 없을 경우 exception
    //         (3) builder을 통해 save
    @PostMapping("/common/codes")
    public ResponseEntity<Long> insert(@RequestBody YongCommonCodeDTO yongCommonCodeDTO){
        Long joinId = yongCommonCodeService.join(yongCommonCodeDTO);
        return ResponseEntity.ok(joinId);
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/common/codes/{id}")
    public ResponseEntity<YongCommonCodeVO> show(@PathVariable("id") Long id){
        YongCommonCodeVO show = yongCommonCodeService.show(id);
        return ResponseEntity.ok(show);
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : ** common-code의 경우, 필수적으로 parent code(group-code) 가 필요
    //         (1) group-code is null -> exception
    //         (2) group-code -> findByIdAndDeleteYnIs -> 없을 경우 exception
    //         (3) 변경메서드를 통해 update
    @PutMapping("/common/codes/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongCommonCodeDTO yongCommonCodeDTO){
        yongCommonCodeService.update(id, yongCommonCodeDTO);
        return ResponseEntity.ok("updated!!");
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete_yn 컬럼 'Y' 처리
    @DeleteMapping("/common/codes/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongCommonCodeService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }

}
