package com.ebe.repositories;

import com.ebe.common.GeneralLogger;
import com.ebe.entities.PosVendorEntity;
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
public class PosVendorRepositoryTest {

    @Autowired
    private PosVendorRepository repository;

    @Test
    public void testGetPosVendors() throws Exception {
        for (int i = 0; i < 3; i++) {
            repository.save(new PosVendorEntity("PosVendor" + i));
        }
        List<PosVendorEntity> posVendors = repository.findAll();
        assertTrue(posVendors.size() >=3);
    }

    @Test
    public void testCreatePosVendor() throws Exception {
        PosVendorEntity posVendorEntity = new PosVendorEntity();
        posVendorEntity.setName("PosVendor1");
        repository.saveAndFlush(posVendorEntity);
        Integer id = posVendorEntity.getId();
        assertThat(id, is(notNullValue()));

        PosVendorEntity again = repository.findOne(id);
        assertThat(again.getId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getName(), is("PosVendor1"));
    }

    @Test
    public void testUpdatePosVendor() throws Exception {
        PosVendorEntity posVendorEntity = new PosVendorEntity();
        posVendorEntity.setName("PosVendor1");
        repository.save(posVendorEntity);
        Integer id = posVendorEntity.getId();
        posVendorEntity.setName("TestPosVendor");
        repository.save(posVendorEntity);
        PosVendorEntity again = repository.findOne(id);
        assertThat(again.getName(), is("TestPosVendor"));
    }

    @Test
    public void testDeletePosVendor() throws Exception{
        PosVendorEntity posVendorEntity = new PosVendorEntity("Test PosVendor");
        this.repository.save(posVendorEntity);
        Integer id = posVendorEntity.getId();
        assertThat(id, is(notNullValue()));
        repository.delete(id);
        PosVendorEntity again = this.repository.findOne(id);
        assertThat(again, is(nullValue()));
    }
}
