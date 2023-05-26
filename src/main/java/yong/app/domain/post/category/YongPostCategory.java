package yong.app.domain.post.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "yong_post_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YongPostCategory extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_post_category_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "delete_yn")
    private String deleteYn;

    // insert 용
    @Builder(builderMethodName = "insertPostCategory")
    public YongPostCategory(String name, String description) {
        this.name = name;
        this.description = description;
        this.deleteYn = "N";
    }

    // update 용
    public void updateCategory(YongPostCategoryDTO yongPostCategoryDTO){
        if(yongPostCategoryDTO.getName() != null) this.name = yongPostCategoryDTO.getName();
        if(yongPostCategoryDTO.getDescription() != null) this.description = yongPostCategoryDTO.getDescription();
    }

    // delete 용
    public void deleteCategory(){
        this.deleteYn = "Y";
    }
}

