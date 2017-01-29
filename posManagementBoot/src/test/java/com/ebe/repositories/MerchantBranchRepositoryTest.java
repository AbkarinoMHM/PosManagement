package com.ebe.repositories;

import com.ebe.entities.AreaEntity;
import com.ebe.entities.RegionEntity;
import com.ebe.entities.MerchantBranchEntity;
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
public class MerchantBranchRepositoryTest {

    @Autowired
    private MerchantBranchRepository merchantBranchRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private AreaRepository areaRepository;
    RegionEntity commonRegion;
    AreaEntity commonArea;

    @Before
    public void init() {
        //create an Area to be used by all branches
        commonRegion = new RegionEntity("Branch Test Region");
        regionRepository.save(commonRegion);
        commonArea = new AreaEntity("Branch Test Area", commonRegion);
        areaRepository.save(commonArea);
    }


    @Test
    public void testGetBranches() throws Exception {
        for (int i = 0; i < 3; i++) {
            merchantBranchRepository.save(new MerchantBranchEntity("Branch" + i, commonArea));
        }
        List<MerchantBranchEntity> areas = merchantBranchRepository.findAll();
        assertTrue(areas.size() >= 3);
    }

    @Test
    public void testCreateBranch() throws Exception {
        MerchantBranchEntity branchEntity = new MerchantBranchEntity("Test Branch", commonArea);
        merchantBranchRepository.save(branchEntity);
        Long id = branchEntity.getId();
        assertThat(id, is(notNullValue()));
        MerchantBranchEntity again = merchantBranchRepository.findOne(id);
        assertThat(again.getId(), is(id));
        assertThat(again.getName(), is("Test Branch"));
    }

    @Test
    public void testUpdateBranch() throws Exception {
        MerchantBranchEntity branchEntity = new MerchantBranchEntity("Test Branch", commonArea);
        merchantBranchRepository.save(branchEntity);
        Long id = branchEntity.getId();
        branchEntity.setName("Test Branch 2");
        merchantBranchRepository.save(branchEntity);
        MerchantBranchEntity again = merchantBranchRepository.findOne(id);
        assertThat(again.getName(), is("Test Branch 2"));
    }

    @Test
    public void testDeleteBranch() throws Exception {
        MerchantBranchEntity branchEntity = new MerchantBranchEntity("Test Branch", commonArea);
        merchantBranchRepository.save(branchEntity);
        Long id = branchEntity.getId();
        assertThat(id, is(notNullValue()));
        merchantBranchRepository.delete(id);
        MerchantBranchEntity again = merchantBranchRepository.findOne(id);
        assertThat(again, is(nullValue()));
    }

}
