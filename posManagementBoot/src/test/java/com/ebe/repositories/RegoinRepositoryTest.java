package com.ebe.repositories;

import com.ebe.entities.RegionEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.ebe.common.GeneralLogger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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
        assertThat(regions.size(), is(3));
    }

    @Test
    public void testCreateRegion() throws Exception {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setRegionName("Region1");
        repository.save(regionEntity);
        Long id = regionEntity.getRegionId();
        assertThat(id, is(notNullValue()));

        RegionEntity again = repository.findOne(id);
        assertThat(again.getRegionId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getRegionName(), is("Region1"));
    }

    @Test
    public void testUpdateRegion() throws Exception {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setRegionName("Region1");
        repository.save(regionEntity);
        Long id = regionEntity.getRegionId();
        regionEntity.setRegionName("TestRegion");
        repository.save(regionEntity);
        RegionEntity again = repository.findOne(id);
        assertThat(again.getRegionName(), is("TestRegion"));
    }
}
