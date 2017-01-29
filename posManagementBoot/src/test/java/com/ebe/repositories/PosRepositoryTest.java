package com.ebe.repositories;

import com.ebe.entities.*;
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
public class PosRepositoryTest {

    @Autowired
    private PosRepository posRepository;
    @Autowired
    private PosVendorRepository vendorRepository;
    @Autowired
    private PosTypeRepository typeRepository;
    @Autowired
    private PosConditionRepository conditionRepository;
    @Autowired
    private PosStatusRepository statusRepository;
    @Autowired
    private MerchantBranchRepository merchantBranchRepository;
    @Autowired
    private VendorBranchRepository vendorBranchRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private RegionRepository regionRepository;

    private RegionEntity commonRegion;
    private AreaEntity commonArea;
    private ProjectEntity commonProject;
    private VendorBranchEntity commonVendorBranch;
    private MerchantBranchEntity commonMervchantBranch;
    private PosStatusEntity commonStatus;
    private PosConditionEntity commonCondition;
    private PosTypeEntity commonType;
    private PosVendorEntity commonVendor;


    @Before
    public void init() {
        this.commonRegion = new RegionEntity("Test Region");
        this.regionRepository.saveAndFlush(commonRegion);
        this.commonArea = new AreaEntity("Test Area", this.commonRegion);
        this.areaRepository.saveAndFlush(this.commonArea);
        this.commonProject = new ProjectEntity("Test Project");
        this.projectRepository.saveAndFlush(this.commonProject);
        this.commonVendorBranch = new VendorBranchEntity("Test Vendor Branch", this.commonArea);
        this.vendorBranchRepository.saveAndFlush(this.commonVendorBranch);
        this.commonMervchantBranch = new MerchantBranchEntity("Test Merchant Branch", this.commonArea);
        this.merchantBranchRepository.saveAndFlush(this.commonMervchantBranch);
        this.commonStatus = new PosStatusEntity(101, "Test Status");
        this.statusRepository.saveAndFlush(this.commonStatus);
        this.commonCondition = new PosConditionEntity(101, "Test Condition");
        this.conditionRepository.saveAndFlush(this.commonCondition);
        this.commonType = new PosTypeEntity("test Type");
        this.typeRepository.saveAndFlush(this.commonType);
        this.commonVendor = new PosVendorEntity("Test Vendor");
        this.vendorRepository.saveAndFlush(this.commonVendor);

    }


    @Test
    public void testGet() throws Exception {
        for (int i = 0; i < 3; i++) {
            this.posRepository.save(new PosEntity("serial " + i, this.commonVendor, this.commonType,
                    this.commonCondition, this.commonStatus, this.commonMervchantBranch,
                    this.commonProject, this.commonVendorBranch));
        }
        List<PosEntity> list = posRepository.findAll();
        assertTrue(list.size() >= 3);
    }

    @Test
    public void testCreate() throws Exception {
        PosEntity entity = new PosEntity("122", this.commonVendor, this.commonType, this.commonCondition, this.commonStatus,
                this.commonMervchantBranch, this.commonProject, this.commonVendorBranch);
        posRepository.save(entity);
        Long id = entity.getId();
        assertThat(id, is(notNullValue()));
        PosEntity again = posRepository.findOne(id);
        assertThat(again.getId(), is(id));
        assertThat(again.getSerialNumber(), is("122"));
    }

    @Test
    public void testUpdate() throws Exception {
        PosEntity entity = new PosEntity("122", this.commonVendor, this.commonType, this.commonCondition, this.commonStatus,
                this.commonMervchantBranch, this.commonProject, this.commonVendorBranch);
        posRepository.save(entity);
        Long id = entity.getId();
        assertThat(id, is(notNullValue()));
        entity.setSerialNumber("333");
        posRepository.save(entity);
        PosEntity again = posRepository.findOne(id);
        assertThat(again.getSerialNumber(), is("333"));
    }

    @Test
    public void testDelete() throws Exception {
        PosEntity entity = new PosEntity("122", this.commonVendor, this.commonType, this.commonCondition, this.commonStatus,
                this.commonMervchantBranch, this.commonProject, this.commonVendorBranch);
        posRepository.save(entity);
        Long id = entity.getId();
        assertThat(id, is(notNullValue()));
        posRepository.delete(id);
        PosEntity again = posRepository.findOne(id);
        assertThat(again, is(nullValue()));
    }



}
