//package yong.app.domain.post.category.impl;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import yong.app.domain.post.category.YongPostCategory;
//import yong.app.domain.post.category.YongPostCategoryRepository;
//import yong.app.domain.post.category.YongPostCategoryService;
//import yong.app.domain.post.post.YongPost;
//import yong.app.domain.post.post.YongPostRepository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class YongPostCategoryServiceImplTest {
//
//    @Autowired
//    YongPostCategoryRepository yongPostCategoryRepository;
//
//    @Autowired
//    YongPostRepository yongPostRepository;
//
//    @Test
//    @DisplayName("카테고리의 게시글들을 모두 가져온다")
//    public void postList() {
//
//
////        YongPostCategory yongPostCategory = YongPostCategory.insertPostCategory()
////                .name("NAME")
////                .description("DESCRIPTION")
////                .build();
//
//
//        YongPostCategory yongPostCategory = YongPostCategory.addPosts("NAME", "DESCRIPTION");
//        YongPostCategory save = yongPostCategoryRepository.save(yongPostCategory);
//
//
//        List<YongPostCategory> children = yongPostCategoryRepository.findChildren();
//        for (YongPostCategory child : children) {
//            System.out.println("child = " + child);
//        }
//
//        assertThat(children.size()).isEqualTo(100);;
//    }
//}