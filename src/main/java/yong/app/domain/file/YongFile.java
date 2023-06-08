package yong.app.domain.file;

import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "delete_yn")
    private String deleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_file_id")
    private YongFile parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<YongFile> child = new ArrayList<>();


    // insert 용
    @Builder(builderMethodName = "insertFileBuilder", toBuilder = true)
    public YongFile(String fileName, String description, YongFile parent) {
        this.fileName = fileName;
        this.description = description;
        this.parent = parent;
        if(parent != null) addChild(parent); // 부모가 있다면, 해당 부모의 자식에 자기자신을 넣는다.
        this.deleteYn = "N";
    }

    public void addChild(YongFile parent){
        parent.getChild().add(this);
    }

    public void removeChild(YongFile parent){
        parent.getChild().remove(this);
    }

    public void updateFile(YongFileDTO yongFileDTO){

        if(yongFileDTO.getFileName() != null) this.fileName = yongFileDTO.getFileName();
        if(yongFileDTO.getDescription() != null) this.description = yongFileDTO.getDescription();
        if(yongFileDTO.getParent() != null) {
            this.parent = yongFileDTO.getParent();
            removeChild(yongFileDTO.getParent());  // 만일 parent가 바뀌면, 기존 부모의 자식에서 제거하고
            addChild(yongFileDTO.getParent());     // 새로운 부모의 자식으로 바꿔넣기
        }
    }

    public void deleteFile(){
        this.deleteYn = "Y";
    }
}
