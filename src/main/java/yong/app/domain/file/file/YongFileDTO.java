package yong.app.domain.file.file;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class YongFileDTO {
    private Long yongFileGroupId;
    private String fileName;
    private String filePath;
    private Integer fileSize;
    private String fileType;
}
