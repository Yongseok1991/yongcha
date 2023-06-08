package yong.app.domain.file.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface YongFileRepository extends JpaRepository<YongFile, Long> {

    @Query("""
        select new yong.app.domain.file.file.YongFileVO(y.id, y.fileName, y.filePath, y.fileSize, y.fileType) 
          from YongFile y 
         where y.yongFileGroup.id = :id
        """)
    List<YongFileVO> findAllFilesByFileGroupId(@Param("id") Long id);
}
