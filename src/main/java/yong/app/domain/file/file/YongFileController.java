package yong.app.domain.file.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "file 컨트롤러", description = "파일그룹 하위의 파일 컨트롤러")
public class YongFileController {

    private final YongFileService yongFileService;

    // GET LIST
    // - 리턴 : vo list
    // - 방법 : findAllByParentIsNull -> 모델매퍼를 통해 vo list로 변경
    @Operation(summary = "show file list", description = "전체 파일 리스트")
    @ApiDocumentResponse
    @GetMapping("/files")
    public StatusResponse list(){
        List<YongFileVO> findFiles = yongFileService.list();
        if(findFiles.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, findFiles, "전체 파일 리스트 조회");
    }

    // INSERT
    // - 리턴 : id (pk)
    // - 방법 : builder 이용 -> 부모 있을 경우, 없을 경우 분기처리
    @Operation(summary = "insert file", description = "파일 삽입")
    @ApiDocumentResponse
    @PostMapping("/files")
    public StatusResponse insert(@RequestBody YongFileDTO yongFileDTO){
        Long joinFile = yongFileService.join(yongFileDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinFile, "파일 생성 성공");
    }

    // GET ONE
    // - 리턴 : vo
    // - 방법 : findById -> 모델매퍼를 통해 vo로 변경
    @Operation(summary = "show file view", description = "파일 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/files/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongFileVO yongFileVO = yongFileService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, yongFileVO, "파일 단건 조회");
    }

    // UPDATE
    // - 리턴 : void
    // - 방법 : 변경 메서드 이용 -> 부모 있을 경우, 없을 경우 분기처리
    @Operation(summary = "file update", description = "파일 업데이트")
    @ApiDocumentResponse
    @PutMapping("/files/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongFileDTO yongFileDTO){
        yongFileService.update(id, yongFileDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }

    // DELETE
    // - 리턴 : void
    // - 방법 : findById -> delete (delete_yn 컬럼 to 'Y')  [자식 있을 경우 삭제 불가능하도록 처리]
    @Operation(summary = "file delete", description = "파일 삭제")
    @ApiDocumentResponse
    @DeleteMapping("/files/{id}")
    public StatusResponse delete(@PathVariable("id") Long id){
        yongFileService.delete(id);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
