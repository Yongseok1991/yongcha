package yong.app.domain.file.group;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

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
    public StatusResponse list(){
        List<YongFileGroupVO> list = fileGroupService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "파일그룹 리스트 반환");
    }

    @GetMapping("/file/groups/with/files")
    public StatusResponse listWithFiles(){
        List<YongFileGroupVO> list = fileGroupService.listWithFiles();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "파일그룹 & 각 파일 리스트 반환");
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder을 통해 save
    @PostMapping("/file/groups")
    public StatusResponse insert(@RequestBody YongFileGroupDTO yongFileGroupDTO){
        Long joinId = fileGroupService.join(yongFileGroupDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "파일 그룹 생성 성공");
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @GetMapping("/file/groups/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongFileGroupVO show = fileGroupService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "파일 그룹 단일건 조회");
    }

    @GetMapping("/file/groups/with/files/{id}")
    public StatusResponse showWithFiles(@PathVariable("id") Long id){
        YongFileGroupVO show = fileGroupService.showWithFiles(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "파일 그룹 & 각 파일 단일건 조회");
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
