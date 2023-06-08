package yong.app.domain.file.group;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongFileGroupController {

    private final YongFileGroupService fileGroupService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAll -> 모델매퍼를 통해 vo list로 변경
    @GetMapping("/file/groups")
    public ResponseEntity<List<YongFileGroupVO>> list(){
        List<YongFileGroupVO> list = fileGroupService.list();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/file/groups/with/files")
    public ResponseEntity<List<YongFileGroupVO>> listWithFiles(){
        List<YongFileGroupVO> list = fileGroupService.listWithFiles();
        return ResponseEntity.ok(list);
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder을 통해 save
    @PostMapping("/file/groups")
    public ResponseEntity<Long> insert(@RequestBody YongFileGroupDTO yongFileGroupDTO){
        Long joinId = fileGroupService.join(yongFileGroupDTO);
        return ResponseEntity.ok(joinId);
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/file/groups/{id}")
    public ResponseEntity<YongFileGroupVO> show(@PathVariable("id") Long id){
        YongFileGroupVO show = fileGroupService.show(id);
        return ResponseEntity.ok(show);
    }

    @GetMapping("/file/groups/with/files/{id}")
    public ResponseEntity<YongFileGroupVO> showWithFiles(@PathVariable("id") Long id){
        YongFileGroupVO show = fileGroupService.showWithFiles(id);
        return ResponseEntity.ok(show);
    }


    // UPDATE
    // - 리턴 : void
    // - 방법 : findById -> 변경메서드를 통해 변경
    @PutMapping("/file/groups/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongFileGroupDTO yongFileGroupDTO){
        fileGroupService.update(id, yongFileGroupDTO);
        return ResponseEntity.ok("updated!!");
    }

}
