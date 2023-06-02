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

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAllByParentIsNull -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/files")
    public ResponseEntity<List<YongFileVO>> list(){
        List<YongFileVO> findFiles = yongFileService.list();
        return  ResponseEntity.ok(findFiles);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용 -> 부모 있을 경우, 없을 경우 분기처리
    @PostMapping("/files")
    public ResponseEntity<Long> insert(@RequestBody YongFileDTO yongFileDTO){
        Long joinFile = yongFileService.join(yongFileDTO);
        return ResponseEntity.ok(joinFile);
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/files/{id}")
    public ResponseEntity<YongFileVO> show(@PathVariable("id") Long id){
        YongFileVO yongFileVO = yongFileService.show(id);
        return ResponseEntity.ok(yongFileVO);
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : 변경 메서드 이용 -> 부모 있을 경우, 없을 경우 분기처리
    @PutMapping("/files/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongFileDTO yongFileDTO){
        yongFileService.update(id, yongFileDTO);
        return ResponseEntity.ok("updated!!");
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete (delete_yn 컬럼 to 'Y')  [자식 있을 경우 삭제 불가능하도록 처리]
    @DeleteMapping("/files/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongFileService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }
}
