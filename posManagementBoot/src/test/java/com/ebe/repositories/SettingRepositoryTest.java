package com.ebe.repositories;

import com.ebe.entities.SettingEntity;
import org.apache.coyote.http2.Setting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by saado on 11/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class SettingRepositoryTest {
    @Autowired
    private SettingRepository repository;

    @Test
    public void TestGetSettingReturnsBasicSettings(){
        List<SettingEntity> settings = this.repository.findAll();
        //at least 3 settings are loaded (CustomerCass, CustomerName, VendorName)
        assertTrue(settings.size()>= 3);
        assertTrue(settings.contains(new SettingEntity("customerClass", "")));
        assertTrue(settings.contains(new SettingEntity("customerName", "")));
        assertTrue(settings.contains(new SettingEntity("vendorName", "")));
    }
}
