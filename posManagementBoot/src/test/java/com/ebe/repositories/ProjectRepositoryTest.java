package com.ebe.repositories;

import com.ebe.common.GeneralLogger;
import com.ebe.entities.ProjectEntity;
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
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository repository;

    @Test
    public void testGetProjects() throws Exception {
        for (int i = 0; i < 3; i++) {
            repository.save(new ProjectEntity("Project" + i));
        }
        List<ProjectEntity> projects = repository.findAll();
        assertThat(projects.size(), is(3));
    }

    @Test
    public void testCreateProject() throws Exception {
        ProjectEntity regionEntity = new ProjectEntity();
        regionEntity.setProjectName("Project1");
        repository.saveAndFlush(regionEntity);
        Long id = regionEntity.getProjectId();
        assertThat(id, is(notNullValue()));

        ProjectEntity again = repository.findOne(id);
        assertThat(again.getProjectId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getProjectName(), is("Project1"));
    }

    @Test
    public void testUpdateProject() throws Exception {
        ProjectEntity regionEntity = new ProjectEntity();
        regionEntity.setProjectName("Project1");
        repository.save(regionEntity);
        Long id = regionEntity.getProjectId();
        regionEntity.setProjectName("TestRegion");
        repository.save(regionEntity);
        ProjectEntity again = repository.findOne(id);
        assertThat(again.getProjectName(), is("TestRegion"));
    }
}
