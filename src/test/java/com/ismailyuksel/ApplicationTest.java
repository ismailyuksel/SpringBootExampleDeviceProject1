package com.ismailyuksel;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(properties = {"spring.profiles.active=Devel"})
@RunWith(SpringRunner.class)
class ApplicationTest {
	
	  @Test
	  public void contextLoads() {
	  }

	  @Test
	  public void testInsertDb() {
		System.out.println("test"); 
	  }

}
