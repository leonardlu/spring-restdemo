package restdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import restdemo.OctusRestdemoApplication;

/**
 * 
 * @author leonardlu
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OctusRestdemoApplication.class)
@WebAppConfiguration
public class OctusRestdemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
