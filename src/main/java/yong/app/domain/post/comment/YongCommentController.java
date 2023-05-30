package yong.app.domain.post.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongCommentController {

    private final YongCommentService yongCommentService;

    @GetMapping("/comments")
    public ResponseEntity<List<YongCommentVO>> list(){
        List<YongCommentVO> list = yongCommentService.list();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/comments")
    public ResponseEntity<Long> save(@RequestBody YongCommentDTO yongCommentDTO){
        Long joinId = yongCommentService.join(yongCommentDTO);
        return ResponseEntity.ok(joinId);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<YongCommentVO> show(@PathVariable("id") Long id){
        YongCommentVO show = yongCommentService.show(id);
        return ResponseEntity.ok(show);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongCommentDTO yongCommentDTO){
        yongCommentService.update(id, yongCommentDTO);
        return ResponseEntity.ok("updated!!");
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongCommentService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }
}
