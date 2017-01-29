package com.ebe.repositories;

import com.ebe.entities.AreaEntity;
import com.ebe.entities.RegionEntity;
import com.ebe.entities.ServiceCenterEntity;
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
public class ServiceCenterRepositoryTest {

    @Autowired
    private ServiceCenterRepository serviceCenterRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private AreaRepository areaRepository;
    RegionEntity commonRegion;
    AreaEntity commonArea;

    @Before
    public void init() {
        //create an Area to be used by all branches
        commonRegion = new RegionEntity("ServiceCenter Test Region");
        regionRepository.save(commonRegion);
        commonArea = new AreaEntity("ServiceCenter Test Area", commonRegion);
        areaRepository.save(commonArea);
    }


    @Test
    public void testGetServiceCenters() throws Exception {
        for (int i = 0; i < 3; i++) {
            serviceCenterRepository.save(new ServiceCenterEntity("ServiceCenter" + i, commonArea));
        }
        List<ServiceCenterEntity> centers = serviceCenterRepository.findAll();
        assertTrue(centers.size() >= 3);
    }

    @Test
    public void testCreateServiceCenter() throws Exception {
        ServiceCenterEntity serviceCenterEntity = new ServiceCenterEntity("Test ServiceCenter", commonArea);
        serviceCenterRepository.save(serviceCenterEntity);
        Long id = serviceCenterEntity.getId();
        assertThat(id, is(notNullValue()));
        ServiceCenterEntity again = serviceCenterRepository.findOne(id);
        assertThat(again.getId(), is(id));
        assertThat(again.getName(), is("Test ServiceCenter"));
    }

    @Test
    public void testUpdateServiceCenter() throws Exception {
        ServiceCenterEntity serviceCenterEntity = new ServiceCenterEntity("Test ServiceCenter", commonArea);
        serviceCenterRepository.save(serviceCenterEntity);
        Long id = serviceCenterEntity.getId();
        serviceCenterEntity.setName("Test ServiceCenter 2");
        serviceCenterRepository.save(serviceCenterEntity);
        ServiceCenterEntity again = serviceCenterRepository.findOne(id);
        assertThat(again.getName(), is("Test ServiceCenter 2"));
    }

    @Test
    public void testDeleteServiceCenter() throws Exception {
        ServiceCenterEntity serviceCenterEntity = new ServiceCenterEntity("Test ServiceCenter", commonArea);
        serviceCenterRepository.save(serviceCenterEntity);
        Long id = serviceCenterEntity.getId();
        assertThat(id, is(notNullValue()));
        serviceCenterRepository.delete(id);
        ServiceCenterEntity again = serviceCenterRepository.findOne(id);
        assertThat(again, is(nullValue()));
    }

}
