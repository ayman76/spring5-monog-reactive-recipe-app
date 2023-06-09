package guru.springframework.repositories.reactive;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.Recipe;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepoTest {

    @Autowired
    RecipeReactiveRepo recipeReactiveRepo;

    @Before
    public void setUp() {
        recipeReactiveRepo.deleteAll().block();
    }

    @Test
    public void testRecipeSave() {
        Recipe recipe = new Recipe();
        recipe.setDescription("qwew");

        recipeReactiveRepo.save(recipe).block();

        Long count = recipeReactiveRepo.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

}
