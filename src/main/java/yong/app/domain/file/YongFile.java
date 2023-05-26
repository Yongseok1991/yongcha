package yong.app.domain.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "yong_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YongFile extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_file_id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_file_id")
    private YongFile parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<YongFile> yongFiles = new ArrayList<>();
}
