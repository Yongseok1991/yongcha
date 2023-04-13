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

    @PostMapping("/boards")
    public ResponseEntity save(Board board) {
        return ResponseEntity.ok(boardRepository.save(board));
    }

    @GetMapping("/boards")
    public ResponseEntity index(Pageable pageable) {
        return ResponseEntity.ok(boardRepository.findAll(pageable));
    }

    @PutMapping("/boards/{uid}")
    public ResponseEntity update(@PathVariable Long uid, Board board) {
        Board boards = boardRepository.save(board);
        return ResponseEntity.ok(board);
    }

    @DeleteMapping("/boards/{uid}")
    public ResponseEntity delete(@PathVariable Long uid) {
        boardRepository.deleteById(uid);
        return new ResponseEntity(HttpStatus.OK);
    }

}
