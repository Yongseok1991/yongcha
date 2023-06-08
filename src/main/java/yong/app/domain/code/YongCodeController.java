package yong.app.domain.code;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yong.app.global.response.StatusCode;
import yong.app.global.response.StatusResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class YongCodeController {

    private final YongCodeService yongCodeService;

    @GetMapping("/codes")
    public StatusResponse list(){
        List<YongCodeVO> list = yongCodeService.list();
        if(list.isEmpty()) return new StatusResponse(StatusCode.NO_CONTENT);
        return new StatusResponse(StatusCode.SUCCESS, list, "코드 리스트 반환");
    }

    @PostMapping("/codes")
    public StatusResponse insert(@Valid @RequestBody YongCodeDTO yongCodeDTO){
        Long joinId = yongCodeService.join(yongCodeDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId, "코드 생성 성공");
    }

    @GetMapping("/codes/{id}")
    public StatusResponse show(@PathVariable("id") Long id){
        YongCodeVO show = yongCodeService.show(id);
        return new StatusResponse(StatusCode.SUCCESS, show, "단일 코드 조회 성공");
    }

    @PutMapping("/codes/{id}")
    public StatusResponse update(@PathVariable("id") Long id, @RequestBody YongCodeDTO yongCodeDTO){
        yongCodeService.update(id, yongCodeDTO);
        return new StatusResponse(StatusCode.SUCCESS);
    }
}
