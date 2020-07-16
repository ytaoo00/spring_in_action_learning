package tacos;

import static org.hamcrest.Matchers.containsString;
import static
org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
//@WebMvcTest arranges for the test to run in the context of a Spring MVC application
//or it allows HomeController to be registered in Spring MVC so one can throw requests against it.

//@WebMvcTest(HomeController.class)
public class HomeControllerTest{
	
	@Autowired
	private MockMvc mockMvc; // injects MockMvc
	
	@Test
	public void testHomePage() throws Exception{
		mockMvc.perform(get("/")) //Performs GEt
		
			.andExpect(status().isOk()) // Expects HTTP 200
			
			.andExpect(view().name("home")) // Expects home view
			
			.andExpect(content().string(containsString("Welcome to..."))); // Expects the content
	}
}