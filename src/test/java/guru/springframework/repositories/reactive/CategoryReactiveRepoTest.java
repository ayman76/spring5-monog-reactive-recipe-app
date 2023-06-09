package guru.springframework.repositories.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.Category;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepoTest {

    @Autowired
    CategoryReactiveRepo categoryReactiveRepo;

    @Before
    public void setUp() {
        categoryReactiveRepo.deleteAll().block();
    }

    @Test
    public void testCategorySave() {
        Category category = new Category();
        category.setDescription("erewrer");

        categoryReactiveRepo.save(category).block();

        Long count = categoryReactiveRepo.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void testFindByDescription() {
        Category category = new Category();
        category.setDescription("qqqq");

        categoryReactiveRepo.save(category).block();

        Category foundedCategory = categoryReactiveRepo.findByDescription("qqqq").block();
        
        assertNotNull(foundedCategory.getId());
        assertEquals("qqqq", foundedCategory.getDescription());

    }
}
