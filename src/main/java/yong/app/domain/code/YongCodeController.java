package yong.app.domain.code;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.ApiDocumentResponse;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "code 컨트롤러", description = "code 컨트롤러")
public class YongCodeController {

    private final YongCodeService yongCodeService;

    @Operation(summary = "show code list", description = "전체 코드 리스트")
    @ApiDocumentResponse
    @GetMapping("/codes")
    public StatusResponse list(){
        List<YongCodeVO> list = yongCodeService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "코드 리스트 반환");
    }

    @Operation(summary = "insert code list", description = "코드 삽입")
    @ApiDocumentResponse
    @PostMapping("/codes")
    public StatusResponse insert(@Valid @RequestBody YongCodeDTO yongCodeDTO){
        Long joinId = yongCodeService.join(yongCodeDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "코드 생성 성공");
    }

    @Operation(summary = "show code view", description = "코드 단건 조회")
    @ApiDocumentResponse
    @GetMapping("/codes/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongCodeVO show = yongCodeService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "단일 코드 조회 성공");
    }

    @Operation(summary = "update code", description = "코드 단건 업데이트")
    @ApiDocumentResponse
    @PutMapping("/codes/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongCodeDTO yongCodeDTO){
        yongCodeService.update(id, yongCodeDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
