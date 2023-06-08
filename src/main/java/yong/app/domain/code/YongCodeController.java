package yong.app.domain.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<YongCodeVO>> list(){
        List<YongCodeVO> list = yongCodeService.list();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/codesList")
    public StatusResponse codeList(){
        List<YongCodeVO> list = yongCodeService.list();

        if(list.isEmpty()) {
            return new StatusResponse(StatusCode.NO_CONTENT);
        }
        return new StatusResponse(StatusCode.SUCCESS, list, "리스트 가져왔다.");
    }

    @PostMapping("/codes")
    public StatusResponse insert(@Valid @RequestBody YongCodeDTO yongCodeDTO){
        Long joinId = yongCodeService.join(yongCodeDTO);
        return new StatusResponse(StatusCode.SUCCESS, joinId);
    }

    @GetMapping("/codes/{id}")
    public ResponseEntity<YongCodeVO> show(@PathVariable("id") Long id){
        YongCodeVO show = yongCodeService.show(id);
        return ResponseEntity.ok(show);
    }

    @PutMapping("/codes/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongCodeDTO yongCodeDTO){
        yongCodeService.update(id, yongCodeDTO);
        return ResponseEntity.ok("updated!!");
    }
}
