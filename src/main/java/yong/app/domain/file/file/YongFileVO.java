package yong.app.domain.file.file;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class YongFileVO {

    private Long id;
    private String fileName;
    private String filePath;
    private Integer fileSize;
    private String fileType;

    // findAllFilesByFileGroupId
    public YongFileVO(Long id, String fileName, String filePath, Integer fileSize, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public YongFileVO(YongFile yongFile){
        this.id = yongFile.getId();
        this.fileName = yongFile.getFileName();
        this.filePath = yongFile.getFilePath();
        this.fileSize = yongFile.getFileSize();
        this.fileType = yongFile.getFileType();
    }
}
