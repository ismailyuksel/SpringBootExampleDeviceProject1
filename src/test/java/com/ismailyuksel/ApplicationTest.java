package com.ismailyuksel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ismailyuksel.controller.ImportDeviceController;
import com.ismailyuksel.controller.SearchDeviceController;
import com.ismailyuksel.model.MobileDeviceImportResultModel;
import com.ismailyuksel.model.MobileDeviceSearchResultModel;

@SpringBootTest(properties = {"spring.profiles.active=Devel"})
@RunWith(SpringRunner.class)
class ApplicationTest {
	
    @Autowired
    ImportDeviceController importDeviceController;
    
    @Autowired
    SearchDeviceController searchDeviceController;
	
	@Test
	public void contextLoads() {
	}
	
	@Before
    @Test
    public void importDeviceToDb() {
        MobileDeviceImportResultModel result = importDeviceController.importMobileDevice();
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getIdsList().size());
    }

	@Test
    public void searchAllDevice() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, null, null, null, null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchFirstDevice() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(1, null, null, null, null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertEquals(1, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchDeviceOs() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, null, null, "Android", null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }

	@Test
    public void searchDeviceOsVersion() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, null, null, null, "9");
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchDeviceBrand() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, "Samsung", null, null, null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchDeviceModel() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, null, "A5s", null, null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchDeviceBrandModel() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, "OPPO", "A5s", null, null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchDeviceBrandModelOs() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, "OPPO", "A5s", "Android", null);
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchDeviceBrandModelOsOsVersion() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, "OPPO", "A5s", "Android", "8.1.0");
        Assert.assertTrue(result.isSuccess());
        Assert.assertNotEquals(0, result.getMobileDeviceList().size());
    }
	
	@Test
    public void searchDeviceEmptyResultForBrand() {
        MobileDeviceSearchResultModel result = searchDeviceController.searchMobileDevice(0, "test_brand", null, null, null);
        Assert.assertTrue(result.isSuccess());
	    Assert.assertEquals(0, result.getMobileDeviceList().size());
    }
	
}
