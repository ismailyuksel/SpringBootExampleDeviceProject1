package com.ismailyuksel;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.ismailyuksel.model.MobileDeviceImportResultModel;
import com.ismailyuksel.model.MobileDeviceSearchResultModel;

// <--NOTE: Run project before start Remote Test. Otherwise test will failure -->
@SpringBootTest(properties = {"spring.profiles.active=Devel"})
@RunWith(SpringRunner.class)
class ApplicationRemoteTest {
    
	private static final String BASE_TEST_URL = "http://localhost:8605";
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void contextLoads() {
	}
	
	@Before
	@Test
	public void importDeviceToDb() {
		try {
		    URI uri = getURI("/import/device");
		 
		    RestTemplate restTemplate = new RestTemplate();
		    ResponseEntity<MobileDeviceImportResultModel> result = restTemplate.getForEntity(uri, MobileDeviceImportResultModel.class);
		    
		    Assert.assertEquals(200, result.getStatusCodeValue());
		    Assert.assertTrue(result.getBody().isSuccess());
		    Assert.assertNotEquals(0, result.getBody().getIdsList().size());
		} catch (Exception e) {
			 logger.error("importDeviceToDb test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchAllDevice() {
		try {
		    URI uri = getURI("/search/device");
		    MobileDeviceSearchResultModel importResult = getSearchResult(uri);
		    
		    Assert.assertTrue(importResult.isSuccess());
		    Assert.assertNotEquals(0, importResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchAllDevice test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchFirstDevice() {
		try {
		    URI uri = getURI("/search/device?id=1");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertEquals(1, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchFirstDevice test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchDeviceOs() {
		try {
		    URI uri = getURI("/search/device?os=Android");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertNotEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceOs test error", e);
			 Assert.assertTrue(false);
		}
	}

	@Test
	public void searchDeviceOsVersion() {
		try {
		    URI uri = getURI("/search/device?osVersion=9");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertNotEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceOsVersion test error", e);
			 Assert.assertTrue(false);
		}
	}

	@Test
	public void searchDeviceBrand() {
		try {
			URI uri = getURI("/search/device?brand=Samsung");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertNotEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceBrand test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchDeviceModel() {
		try {
			URI uri = getURI("/search/device?model=A5s");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertNotEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceModel test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchDeviceBrandModel() {
		try {
		    URI uri = getURI("/search/device?brand=OPPO&model=A5s");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertNotEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceBrandModel test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchDeviceBrandModelOs() {
		try {
		    URI uri = getURI("/search/device?brand=OPPO&model=A5s&os=Android");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertNotEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceBrandModelOs test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchDeviceBrandModelOsOsVersion() {
		try {
		    URI uri = getURI("/search/device?brand=OPPO&model=A5s&os=Android&osVersion=8.1.0");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertNotEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceBrandModelOsOsVersion test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchDeviceEmptyResultForBrand() {
		try {
		    URI uri = getURI("/search/device?brand=test_brand");
		    MobileDeviceSearchResultModel searchResult = getSearchResult(uri);
		    
		    Assert.assertTrue(searchResult.isSuccess());
		    Assert.assertEquals(0, searchResult.getMobileDeviceList().size());
		} catch (Exception e) {
			 logger.error("searchDeviceEmptyResultForBrand test error", e);
			 Assert.assertTrue(false);
		}
	}
	
	private URI getURI(String urlPath) throws URISyntaxException {
	    String url = BASE_TEST_URL + urlPath;
	    URI uri = new URI(url);
	    return uri;
	}
	
	private MobileDeviceSearchResultModel getSearchResult(URI uri) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MobileDeviceSearchResultModel> result = restTemplate.getForEntity(uri, MobileDeviceSearchResultModel.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		return result.getBody();
	}
	
}
