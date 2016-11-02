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
        assertThat(posConditions.size(), is(3));
    }

    @Test
    public void testCreatePosCondition() throws Exception {
        PosConditionEntity posConditionEntity = new PosConditionEntity(1, "Pos Condition 1");
        repository.saveAndFlush(posConditionEntity);
        Integer id = posConditionEntity.getPosConditionId();
        assertThat(id, is(notNullValue()));

        PosConditionEntity again = repository.findOne(id);
        assertThat(again.getPosConditionId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getPosConditionName(), is("Pos Condition 1"));
    }

    @Test
    public void testUpdatePosCondition() throws Exception {
        PosConditionEntity posConditionEntity = new PosConditionEntity(1, "Pos Condition 1");
        repository.save(posConditionEntity);
        Integer id = posConditionEntity.getPosConditionId();
        posConditionEntity.setPosConditionName("Test Pos Condition");
        repository.save(posConditionEntity);
        PosConditionEntity again = repository.findOne(id);
        assertThat(again.getPosConditionName(), is("Test Pos Condition"));
    }
}
