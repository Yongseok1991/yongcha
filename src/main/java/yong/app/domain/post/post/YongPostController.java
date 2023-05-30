package yong.app.domain.post.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class YongPostController {

    private final YongPostService yongPostService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAlll -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/posts")
    public ResponseEntity<List<YongPostVO>> list(){
        List<YongPostVO> list = yongPostService.list();
        return ResponseEntity.ok(list);
    }


    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용 -> 무조건 부모가 있어야함 / file의 경우 있고 없고를 분기처리
    @PostMapping("/posts")
    public ResponseEntity<Long> insert(@RequestBody YongPostDTO yongPostDTO){
        Long joinId = yongPostService.join(yongPostDTO);
        return ResponseEntity.ok(joinId);
    }


    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경 메서드를 통해 '변경 지점이 엔티티로 모이도록' 하였다. (file의 경우 있고 없고를 분기처리)
    @PutMapping("/posts/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongPostDTO yongPostDTO){
        yongPostService.update(id, yongPostDTO);
        return ResponseEntity.ok("updated!!");
    }


    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 VO로 변경
    @GetMapping("/posts/{id}")
    public ResponseEntity<YongPostVO> show(@PathVariable("id") Long id){
        YongPostVO findPost = yongPostService.show(id);
        return ResponseEntity.ok(findPost);
    }


    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete (delete_yn 컬럼 to 'Y')
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongPostService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }

}