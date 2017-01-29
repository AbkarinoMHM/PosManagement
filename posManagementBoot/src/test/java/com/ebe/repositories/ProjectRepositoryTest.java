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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
        assertTrue(projects.size() >=3);
    }

    @Test
    public void testCreateProject() throws Exception {
        ProjectEntity regionEntity = new ProjectEntity();
        regionEntity.setName("Project1");
        repository.saveAndFlush(regionEntity);
        Long id = regionEntity.getId();
        assertThat(id, is(notNullValue()));

        ProjectEntity again = repository.findOne(id);
        assertThat(again.getId(), is(id));
        GeneralLogger.info(this.getClass(), again.toString());
        assertThat(again.getName(), is("Project1"));
    }

    @Test
    public void testUpdateProject() throws Exception {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName("Project1");
        repository.save(projectEntity);
        Long id = projectEntity.getId();
        projectEntity.setName("TestRegion");
        repository.save(projectEntity);
        ProjectEntity again = repository.findOne(id);
        assertThat(again.getName(), is("TestRegion"));
    }

    @Test
    public void testDeleteProject() throws Exception{
        ProjectEntity projectEntity = new ProjectEntity("Test Project");
        this.repository.save(projectEntity);
        Long id = projectEntity.getId();
        assertThat(id, is(notNullValue()));
        repository.delete(id);
        ProjectEntity again = this.repository.findOne(id);
        assertThat(again, is(nullValue()));
    }
}
