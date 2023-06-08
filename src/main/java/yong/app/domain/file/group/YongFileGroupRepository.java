package yong.app.domain.file.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface YongFileGroupRepository extends JpaRepository<YongFileGroup, Long> {

    Optional<YongFileGroup> findByIdAndDeleteYnIs(Long id, String deleteYn);

    @Query("""
        select new yong.app.domain.file.group.YongFileGroupVO(yfg.id, yfg.fileGroupName, yfg.description) 
          from YongFileGroup yfg 
         where yfg.id = :id
    """)
    Optional<YongFileGroupVO> findPostFileGroupById(@Param("id") Long id);


    @Query("""
        select new yong.app.domain.file.group.YongFileGroupVO(yfg.id, yfg.fileGroupName, yfg.description) 
          from YongFileGroup yfg 
    """)
    List<YongFileGroupVO> findAllFileGroup();

}

