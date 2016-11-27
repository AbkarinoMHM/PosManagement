package com.ebe.specifications;

import com.ebe.SearchSpecifications.GenericSpecification;
import com.ebe.SearchSpecifications.SearchCriteria;
import com.ebe.entities.ProjectEntity;
import com.ebe.repositories.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;

/**
 * Created by saado on 11/23/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ProjectSpecificationsTest {
    @Autowired
    private ProjectRepository repository;
    private ProjectEntity project1;
    private ProjectEntity project2;
    @Before
    public void init() {
        project1 = new ProjectEntity("Project 1");
        project2 = new ProjectEntity("Project 2");
        repository.save(project1);
        repository.save(project2);
    }

    @Test
    public void testSearchByProjectName(){
        GenericSpecification spec = new GenericSpecification<ProjectEntity>(new SearchCriteria("projectName", ":", "Project 1"));

        List<ProjectEntity> results = repository.findAll(spec);
        assertThat(project1, isIn(results));
    }
}
