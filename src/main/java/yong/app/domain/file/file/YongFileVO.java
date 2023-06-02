package yong.app.domain.file.file;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class YongFileVO {

    private Long id;
    private String fileName;
    private String filePath;
    private Integer fileSize;
    private String fileType;

//    private Long id;
//    private String fileName;
//    private String description;
//    private String deleteYn;
//    private List<YongFileVO> child;
}
