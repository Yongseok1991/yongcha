package yong.app.domain.post.category;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongPostCategoryController {

    private final YongPostCategoryService yongPostCategoryService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> vo로 변경
    @GetMapping("/categories")
    public ResponseEntity<List<YongPostCategoryVO>> list(){
        List<YongPostCategoryVO> listCategory = yongPostCategoryService.list();
        return ResponseEntity.ok(listCategory);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용
    @PostMapping("/categories")
    public ResponseEntity<Long> join(@RequestBody YongPostCategoryDTO yongPostCategoryDTO){
        Long joinId = yongPostCategoryService.join(yongPostCategoryDTO);
        return ResponseEntity.ok(joinId);
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경 메서드를 통해 '변경 지점이 엔티티로 모이도록' 하였다.
    @PutMapping("/categories/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongPostCategoryDTO yongPostCategoryDTO){
        yongPostCategoryService.update(id, yongPostCategoryDTO);
        return ResponseEntity.ok("updated!!");
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 VO로 변경
    @GetMapping("/categories/{id}")
    public ResponseEntity<YongPostCategoryVO> show(@PathVariable("id") Long id){
        YongPostCategoryVO findUser = yongPostCategoryService.show(id);
        return ResponseEntity.ok(findUser);
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete (delete_yn 컬럼 to 'Y')
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongPostCategoryService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }
}
