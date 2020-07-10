package tacos;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//this checks that the spring application context can be loaded successfully.

//@RunWith is a Junit annotation, providing a test runner that guides Junit in running a test.
//basically we use a plugin to Junit and run the program against Springrunner.
@RunWith(SpringRunner.class)
//@SpringBootTest tells Junit to bootstrap the test with Spring Boot capabilities. 
@SpringBootTest
class TacoCloudApplicationTests {

	// this method prompt the two annotations to do their job and load Spring application context.
	@Test
	void contextLoads() {
	}

}
