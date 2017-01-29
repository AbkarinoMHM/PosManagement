package com.ebe.repositories;

import com.ebe.entities.AreaEntity;
import com.ebe.entities.RegionEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private RegionRepository regionRepository;
    RegionEntity commonRegion;

    @Before
    public void init() {
        //create a region to be used by all areas
        commonRegion = new RegionEntity("Area Test Region");
        regionRepository.save(commonRegion);
    }


    @Test
    public void testGetAreas() throws Exception {
        for (int i = 0; i < 3; i++) {
            areaRepository.save(new AreaEntity("Area" + i, commonRegion));
        }
        List<AreaEntity> areas = areaRepository.findAll();
        assertTrue(areas.size() >= 3);
    }

    @Test
    public void testCreateArea() throws Exception {
        AreaEntity areaEntity = new AreaEntity("Test Area", commonRegion);
        areaRepository.save(areaEntity);
        Long id = areaEntity.getId();
        assertThat(id, is(notNullValue()));
        AreaEntity again = areaRepository.findOne(id);
        assertThat(again.getId(), is(id));
        assertThat(again.getName(), is("Test Area"));
    }

    @Test
    public void testUpdateArea() throws Exception {
        AreaEntity areaEntity = new AreaEntity("Test Area", commonRegion);
        areaRepository.save(areaEntity);
        Long id = areaEntity.getId();
        assertThat(id, is(notNullValue()));
        areaEntity.setName("Test Area 2");
        areaRepository.save(areaEntity);
        AreaEntity again = areaRepository.findOne(id);
        assertThat(again.getName(), is("Test Area 2"));
    }

    @Test
    public void testDeleteArea() throws Exception{
        AreaEntity areaEntity = new AreaEntity("Test Area", commonRegion);
        areaRepository.save(areaEntity);
        Long id = areaEntity.getId();
        assertThat(id, is(notNullValue()));
        areaRepository.delete(id);
        AreaEntity again = areaRepository.findOne(id);
        assertThat(again, is(nullValue()));
    }

    @Test
    public void testFindByAreaNameIgnoreCaseContaining() throws Exception{
        AreaEntity areaEntity = new AreaEntity("Test Area abc", commonRegion);
        areaRepository.save(areaEntity);
        Long id = areaEntity.getId();
        assertThat(id, is(notNullValue()));
        List<AreaEntity> again = areaRepository.findByNameIgnoreCaseContaining("abc");
        assertTrue(again.size() >= 1);
    }

    @Test
    public void testFindByRegionAndAreaNameIgnoreCaseContaining() throws Exception{
        AreaEntity areaEntity = new AreaEntity("Test Area xyz", commonRegion);
        areaRepository.save(areaEntity);
        Long id = areaEntity.getId();
        assertThat(id, is(notNullValue()));
        List<AreaEntity> again = areaRepository.findByRegionAndNameIgnoreCaseContaining(this.commonRegion, "xyz");
        assertTrue(again.size() >= 1);

    }

}
