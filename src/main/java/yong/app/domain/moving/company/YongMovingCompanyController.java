package yong.app.domain.moving.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class YongMovingCompanyController {

    private final YongMovingCompanyService yongMovingCompanyService;

    @GetMapping("/moving/companies")
    public ResponseEntity<List<YongMovingCompanyVO>> list(){
        List<YongMovingCompanyVO> list = yongMovingCompanyService.list();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/moving/companies")
    public ResponseEntity<Long> insert(@RequestBody YongMovingCompanyDTO yongMovingCompanyDTO){
        Long joinId = yongMovingCompanyService.join(yongMovingCompanyDTO);
        return ResponseEntity.ok(joinId);
    }

    @GetMapping("/moving/companies/{id}")
    public ResponseEntity<YongMovingCompanyVO> show(@PathVariable("id") Long id){
        YongMovingCompanyVO show = yongMovingCompanyService.show(id);
        return ResponseEntity.ok(show);
    }

    @PutMapping("/moving/companies/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody YongMovingCompanyDTO yongMovingCompanyDTO){
        yongMovingCompanyService.update(id, yongMovingCompanyDTO);
        return ResponseEntity.ok("updated!!");
    }

    @DeleteMapping("/moving/companies/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        yongMovingCompanyService.delete(id);
        return ResponseEntity.ok("deleted!!");
    }
}
