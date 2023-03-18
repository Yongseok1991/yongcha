package yong.app.visa.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardRepository boardRepository;

    @GetMapping("/boards")
    public ResponseEntity index(Pageable pageable) {
        return ResponseEntity.ok(boardRepository.findAll(pageable));
    }
    @PostMapping("/boards")
    public ResponseEntity save(Board board) {
        return ResponseEntity.ok(boardRepository.save(board));
    }
    @PutMapping("/boards/{uid}")
    public ResponseEntity update(@PathVariable Long uid, Board board) {
        return ResponseEntity.ok(boardRepository.save(board));
    }
    @DeleteMapping("/boards/{uid}")
    public ResponseEntity delete(@PathVariable Long uid) {
        boardRepository.deleteById(uid);
        return new ResponseEntity(HttpStatus.OK);
    }

}
