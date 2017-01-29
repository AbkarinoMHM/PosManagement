package com.ebe.repositories;

import com.ebe.entities.AreaEntity;
import com.ebe.entities.RegionEntity;
import com.ebe.entities.TechnicianEntity;
import org.junit.Before;
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
public class TechnicianRepositoryTest {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Test
    public void testGetTechnician() throws Exception {
        for (int i = 0; i < 3; i++) {
            technicianRepository.save(new TechnicianEntity("Technician" + i));
        }
        List<TechnicianEntity> technicians = technicianRepository.findAll();
        assertTrue(technicians.size() >= 3);
    }

    @Test
    public void testCreateServiceCenter() throws Exception {
        TechnicianEntity technicianEntity = new TechnicianEntity("Test Technician");
        technicianRepository.save(technicianEntity);
        Long id = technicianEntity.getId();
        assertThat(id, is(notNullValue()));
        TechnicianEntity again = technicianRepository.findOne(id);
        assertThat(again.getId(), is(id));
        assertThat(again.getName(), is("Test Technician"));
    }

    @Test
    public void testUpdateServiceCenter() throws Exception {
        TechnicianEntity technicianEntity = new TechnicianEntity("Test Technician");
        technicianRepository.save(technicianEntity);
        Long id = technicianEntity.getId();
        technicianEntity.setName("Test Technician 2");
        technicianRepository.save(technicianEntity);
        TechnicianEntity again = technicianRepository.findOne(id);
        assertThat(again.getName(), is("Test Technician 2"));
    }

    @Test
    public void testDeleteServiceCenter() throws Exception {
        TechnicianEntity technicianEntity = new TechnicianEntity("Test Technician");
        technicianRepository.save(technicianEntity);
        Long id = technicianEntity.getId();
        assertThat(id, is(notNullValue()));
        technicianRepository.delete(id);
        TechnicianEntity again = technicianRepository.findOne(id);
        assertThat(again, is(nullValue()));
    }

}
