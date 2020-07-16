package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//the WebMvcConfigurer interface:
//it defines several methods for configuring Spring MCV and provides default implementation of all the methods.

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	
	//the addViewControllers() method is given a ViewControllerRegistry that one can use 
	//to register one or more view controller. 
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		// here we call addViewController() on the registry, 
		//passing in "/", which is the path for which your view controller will handle GET requests
		//this method returns a ViewControllerREgistration object, on which you immediately call setViewName()
		//to specify home as the view that a request for “/” should be forawrd to. 
		registry.addViewController("/login");
	}
}
