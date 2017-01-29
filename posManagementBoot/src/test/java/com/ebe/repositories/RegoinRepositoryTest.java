package com.ebe.repositories;

import com.ebe.entities.RegionEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.ebe.common.GeneralLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by saado on 10/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class RegoinRepositoryTest {

    @Autowired
    private RegionRepository repository;

    @Test
    public void testGetRegions() throws Exception {
        for (int i = 0; i < 3; i++) {
            repository.save(new RegionEntity("Region" + i));
        }
        List<RegionEntity> regions = repository.findAll();
        assertTrue(regions.size() >= 3);
    }

    @Test
    public void testCreateRegion() throws Exception {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setName("Region1");
        repository.saveAndFlush(regionEntity);
        Integer id = regionEntity.getId();
        assertThat(id, is(notNullValue()));

        RegionEntity again = repository.findOne(id);
        assertThat(again.getId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getName(), is("Region1"));
    }

    @Test
    public void testUpdateRegion() throws Exception {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setName("Region1");
        repository.save(regionEntity);
        Integer id = regionEntity.getId();
        regionEntity.setName("TestRegion");
        repository.save(regionEntity);
        RegionEntity again = repository.findOne(id);
        assertThat(again.getName(), is("TestRegion"));
    }

    @Test
    public void testDeleteRegion() throws Exception{
        RegionEntity projectEntity = new RegionEntity("Test Region");
        this.repository.save(projectEntity);
        Integer id = projectEntity.getId();
        assertThat(id, is(notNullValue()));
        repository.delete(id);
        RegionEntity again = this.repository.findOne(id);
        assertThat(again, is(nullValue()));
    }
}
