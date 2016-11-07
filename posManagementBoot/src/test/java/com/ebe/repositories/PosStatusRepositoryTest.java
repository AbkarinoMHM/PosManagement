package com.ebe.repositories;

import com.ebe.common.GeneralLogger;
import com.ebe.entities.PosStatusEntity;
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
public class PosStatusRepositoryTest {
    @Autowired
    private PosStatusRepository repository;

    @Test
    public void testGetPosStatus() throws Exception {
        for (int i = 1; i < 4; i++) {
            repository.save(new PosStatusEntity(i, "Pos Status" + i));
        }
        List<PosStatusEntity> posStatusList = repository.findAll();
        assertThat(posStatusList.size(), is(3));
    }

    @Test
    public void testCreatePosStatus() throws Exception {
        PosStatusEntity posStatusEntity = new PosStatusEntity(1, "Pos Status 1");
        repository.saveAndFlush(posStatusEntity);
        Integer id = posStatusEntity.getPosStatusId();
        assertThat(id, is(notNullValue()));

        PosStatusEntity again = repository.findOne(id);
        assertThat(again.getPosStatusId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getPosStatusName(), is("Pos Status 1"));
    }

    @Test
    public void testUpdatePosStatus() throws Exception {
        PosStatusEntity posStatusEntity = new PosStatusEntity(1, "Pos Status 1");
        repository.save(posStatusEntity);
        Integer id = posStatusEntity.getPosStatusId();
        posStatusEntity.setPosStatusName("Test Pos status");
        repository.save(posStatusEntity);
        PosStatusEntity again = repository.findOne(id);
        assertThat(again.getPosStatusName(), is("Test Pos status"));
    }
}