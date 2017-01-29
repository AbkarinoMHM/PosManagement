package com.ebe.repositories;

import com.ebe.common.GeneralLogger;
import com.ebe.entities.PosConditionEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by saado on 10/27/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class PosConditionRepositoryTest {
    @Autowired
    private PosConditionRepository repository;

    @Test
    public void testGetPosConditions() throws Exception {
        for (int i = 1; i < 4; i++) {
            repository.save(new PosConditionEntity(i, "Pos Condition" + i));
        }
        List<PosConditionEntity> posConditions = repository.findAll();
        assertTrue(posConditions.size() >= 3);
    }

}
