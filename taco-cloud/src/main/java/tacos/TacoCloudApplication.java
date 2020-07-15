package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;

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
	
	//here we load the initial data for ingredient
	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) {
	    return new CommandLineRunner() {
	      @Override
	      public void run(String... args) throws Exception {
	        repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
	        repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
	        repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
	        repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
	        repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
	        repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
	        repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
	        repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
	        repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
	        repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
	      }
	    };
	 }
}


//coding habit: creating separate configuration class for anything that isn't autoconfigured.