package com.ebe.repositories;

import com.ebe.common.GeneralLogger;
import com.ebe.entities.AreaEntity;
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
 * Created by saado on 10/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AreaRepositoryTest {

    @Autowired
    private AreaRepository repository;

    @Test
    public void testGetAreas() throws Exception {
//        for (int i = 0; i < 3; i++) {
//            repository.save(new AreaEntity("Area" + i, 1));
//        }
//        List<AreaEntity> areas = repository.findAll();
//        assertThat(areas.size(), is(3));
    }

    @Test
    public void testCreateProject() throws Exception {
//        AreaEntity regionEntity = new AreaEntity();
//        regionEntity.setProjectName("Project1");
//        repository.saveAndFlush(regionEntity);
//        Long id = regionEntity.getProjectId();
//        assertThat(id, is(notNullValue()));
//
//        AreaEntity again = repository.findOne(id);
//        assertThat(again.getProjectId(), is(id));
//        GeneralLogger.info(this.getClass(), again.toString());
//        assertThat(again.getProjectName(), is("Project1"));
    }

    @Test
    public void testUpdateProject() throws Exception {
//        AreaEntity regionEntity = new AreaEntity();
//        regionEntity.setProjectName("Project1");
//        repository.save(regionEntity);
//        Long id = regionEntity.getProjectId();
//        regionEntity.setProjectName("TestRegion");
//        repository.save(regionEntity);
//        AreaEntity again = repository.findOne(id);
//        assertThat(again.getProjectName(), is("TestRegion"));
    }
}
