package yong.app.domain.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yong.app.domain.file.file.YongFile;
import yong.app.domain.file.file.YongFileRepository;
import yong.app.domain.file.group.YongFileGroup;
import yong.app.domain.file.group.YongFileGroupRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class YongFileCommon {

    private final YongFileRepository fileRepository;
    private final YongFileGroupRepository fileGroupRepository;

    // add & del files
    @Transactional
    public YongFileGroup addFiles(List<String> addFiles, List<Long> delFiles, Long fileGroupId){

        // [1] new insert
        if(fileGroupId == 0L){

            // (1) make parent file group
            YongFileGroup fileGroup = YongFileGroup.insertFileGroupBuilder().build();
            YongFileGroup fileGroupSave = fileGroupRepository.save(fileGroup);

            // (2) then save files
            for (String fileName : addFiles) {
                YongFile file = YongFile.insertFileBuilder()
                        .yongFileGroup(fileGroupSave)
                        .fileName(fileName)
                        .build();
                fileRepository.save(file);
            }

            return fileGroupSave;
        }

        // [2] update files

        // (1) find parent file
        YongFileGroup fileGroup = fileGroupRepository.findByIdAndDeleteYnIs(fileGroupId, "N")
                .orElseThrow(() -> new NoSuchElementException("there is no file"));

        // (2) del files - fine by id & parentId
        for(Long fileId : delFiles){
            YongFile delFile = fileRepository.findById(fileId).orElseThrow(() -> new NoSuchElementException("there is no file"));
            fileRepository.delete(delFile);
        }

        // (3) add files
        for (String fileName : addFiles) {
            YongFile file = YongFile.insertFileBuilder()
                    .yongFileGroup(fileGroup)
                    .fileName(fileName)
                    .build();
            fileRepository.save(file);
        }

        return fileGroup;
    }

}
