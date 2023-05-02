package yong.app.domain.board;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yong.app.global.base.BaseTimeEntity;
import yong.app.domain.user.YongUser;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column(nullable = false, length = 100)
    private String title;

    //섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨
    @Lob
    private String content;

    private int count; // 조회수

    // DB는 오브젝트를 저장할 수 없다. FK // 자바는 오브젝트를 저장할 수 있다.
    // Many  = Board , one = User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="userUid")
    private YongUser user;

}
