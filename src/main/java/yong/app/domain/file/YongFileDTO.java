package yong.app.domain.file;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class YongFileDTO {
    private String fileName;
    private String description;
    private Long parentFileId;
}
