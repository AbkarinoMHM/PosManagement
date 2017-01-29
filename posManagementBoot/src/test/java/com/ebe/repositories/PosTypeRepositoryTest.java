package com.ebe.repositories;

import com.ebe.common.GeneralLogger;
import com.ebe.entities.PosTypeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by saado on 10/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class PosTypeRepositoryTest {

    @Autowired
    private PosTypeRepository repository;

    @Test
    public void testGetPosTypes() throws Exception {
        for (int i = 0; i < 3; i++) {
            repository.save(new PosTypeEntity("PosType" + i));
        }
        List<PosTypeEntity> posTypes = repository.findAll();
        assertTrue(posTypes.size() >=3);
    }

    @Test
    public void testCreatePosType() throws Exception {
        PosTypeEntity posTypeEntity = new PosTypeEntity();
        posTypeEntity.setName("PosType1");
        repository.saveAndFlush(posTypeEntity);
        Integer id = posTypeEntity.getId();
        assertThat(id, is(notNullValue()));

        PosTypeEntity again = repository.findOne(id);
        assertThat(again.getId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getName(), is("PosType1"));
    }

    @Test
    public void testUpdatePosType() throws Exception {
        PosTypeEntity posTypeEntity = new PosTypeEntity();
        posTypeEntity.setName("PosType1");
        repository.save(posTypeEntity);
        Integer id = posTypeEntity.getId();
        posTypeEntity.setName("TestPosType");
        repository.save(posTypeEntity);
        PosTypeEntity again = repository.findOne(id);
        assertThat(again.getName(), is("TestPosType"));
    }

    @Test
    public void testDeletePosType() throws Exception{
        PosTypeEntity posTypeEntity = new PosTypeEntity("Test PosType");
        this.repository.save(posTypeEntity);
        Integer id = posTypeEntity.getId();
        assertThat(id, is(notNullValue()));
        repository.delete(id);
        PosTypeEntity again = this.repository.findOne(id);
        assertThat(again, is(nullValue()));
    }
}
