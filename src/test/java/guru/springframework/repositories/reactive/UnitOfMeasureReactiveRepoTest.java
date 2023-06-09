package guru.springframework.repositories.reactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepoTest {

    @Autowired
    UnitOfMeasureReactiveRepo unitOfMeasureReactiveRepo;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepo.deleteAll().block();
    }

    @Test
    public void testUnitOfMeasureSave() throws Exception {

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("qqqq");

        unitOfMeasureReactiveRepo.save(unitOfMeasure).block();

        Long count = unitOfMeasureReactiveRepo.count().block();

        assertEquals(Long.valueOf(1L), count);

    }

    @Test
    public void testFindByDescription() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("qqqq");

        unitOfMeasureReactiveRepo.save(unitOfMeasure).block();

        UnitOfMeasure foundedUnitOfMeasure = unitOfMeasureReactiveRepo.findByDescription("qqqq").block();

        assertNotNull(foundedUnitOfMeasure.getId());
        assertEquals("qqqq", foundedUnitOfMeasure.getDescription());

    }

}
