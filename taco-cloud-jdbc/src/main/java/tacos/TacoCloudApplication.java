package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//this annotation is a composite application that combines three other annotation
//@SpringBootConfiguration: Designate  this class as a configuration class.
//@EnableAutoCOnfiguration
//@ComponentScan: this enables component scanning and 
//lets one declares other class with annotations like @Component, @Controller, etc.
//so that Spring can automatically discover them and register them as components in 
//Spring application context
@SpringBootApplication 
public class TacoCloudApplication {

	//run when the JAR file is executed.
	//pass both the configuration class and the command line arguements to run
	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}


//coding habit: creating separate configuration class for anything that isn't autoconfigured.