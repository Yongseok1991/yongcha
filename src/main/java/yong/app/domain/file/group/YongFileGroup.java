package yong.app.domain.file.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import yong.app.domain.file.file.YongFile;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "yong_file_group")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class YongFileGroup extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_file_group_id")
    private Long id;

    @Column(name = "file_group_name")
    private String fileGroupName;

    private String description;

    @Column(name = "delete_yn")
    private String deleteYn;

    @OneToMany(mappedBy = "yongFileGroup")
    private List<YongFile> files = new ArrayList<>();

    @Builder(builderMethodName = "insertFileGroupBuilder")
    public YongFileGroup(String fileGroupName, String description) {
        this.fileGroupName = fileGroupName;
        this.description = description;
        this.deleteYn = "N";
    }

    public void updateFileGroup(YongFileGroupDTO fileGroupDTO){
        if(fileGroupDTO.getFileGroupName() != null) this.fileGroupName = fileGroupDTO.getFileGroupName();
        if(fileGroupDTO.getDescription() != null) this.description = fileGroupDTO.getDescription();
    }
}
