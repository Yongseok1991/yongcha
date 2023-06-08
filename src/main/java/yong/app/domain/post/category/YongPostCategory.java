package yong.app.domain.post.category;

import lombok.*;
import yong.app.domain.post.post.YongPost;
import yong.app.global.base.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "yong_post_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YongPostCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yong_post_category_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "delete_yn")
    private String deleteYn;

    @OneToMany(mappedBy = "postCategory" , cascade = CascadeType.ALL)
    private List<YongPost> posts = new ArrayList<>();

    // insert 용
    @Builder(builderMethodName = "insertPostCategory")
    public YongPostCategory(String name, String description) {
        this.name = name;
        this.description = description;
        this.deleteYn = "N";
    }

    // update 용
    public void updateCategory(YongPostCategoryDTO yongPostCategoryDTO) {
        if (yongPostCategoryDTO.getName() != null) this.name = yongPostCategoryDTO.getName();
        if (yongPostCategoryDTO.getDescription() != null) this.description = yongPostCategoryDTO.getDescription();
    }

    // delete 용
    public void deleteCategory() {
        this.deleteYn = "Y";
    }

    public void addPosts(YongPost yongPost){
        this.posts.add(yongPost);
    }
}

