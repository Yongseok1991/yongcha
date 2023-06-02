package yong.app.domain.moving.company;

import lombok.Getter;
import lombok.Setter;
import yong.app.domain.file.file.YongFile;
import yong.app.domain.file.group.YongFileGroup;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongMovingCompanyDTO {

    private String name;
    private String description;

    private List<String> addFiles = new ArrayList<>();
    private List<Long> delFiles = new ArrayList<>();

    private Long yongFileGroupId;
}
