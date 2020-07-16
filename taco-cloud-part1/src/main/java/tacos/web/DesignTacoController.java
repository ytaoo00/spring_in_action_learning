// this a a controller which handles HTTP requests and either hand a request off to a view
// to render HTML or write data directly to the body of a response(RESTFUL)

package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;

//@Slf4j is a lombok-provided annotation that automatically generates
//an SLF4J Logger in the class at runtime
@Slf4j
//@Controller serves to identify this class as a controller and to mark it as a candidate for
//component scanning, so that Spring will discover it and automatically create an
//instance of DesignTacoController as a bean in the Spring application context.
@Controller
//@RequestMapping speicifies the kind of requests that this controller handles.
//in this case it sepcifies that DesignTacoController will handle requests whose path begins with /design
@RequestMapping("/design")
//The class-level @SessionAttributes annotation specifies any model object(in this case order)
//that should be kept in session and available across multiple request.
@SessionAttributes("order")
public class DesignTacoController {
	
	
	private final IngredientRepository ingredientRepo;
	private TacoRepository designRepo;
	
	//inject the JdbcIngredientRepository and TacoRepository into DesignTacoCOntroller
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
		this.ingredientRepo = ingredientRepo;
		this.designRepo = designRepo;
	}
	
	//when a get request is received for /design, show DesignForm() will be called to handle the request
	@GetMapping
	public String showDesigordernForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		//makes a call to the injected IngredientRepository's findAll() mehtod.
		//which fetches all the ingredients from the database before filtering them into distinct type in the model
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
				filterByType(ingredients, type));
		}
		
		//Model is an object that ferries data between a controller and whatever view is charged with rendering that data.
		//data that’s placed in Model attributes is copied into the servlet response attributes, where the view can find them
		
		
		//model.addAttribute("design", new Taco()); //add design to model
		
		return "design"; //logical name of the view that will be used to render the model to the browser. 
	}
	
	// the @ModelAttribute ensures that object will be created in the model
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	
	//handle receiving end of the POST request by the <form> 
	//The @Valid annotation tells Spring MVC to perform validation on the submitted 
	//object after it’s bound to the submitted form data and before the processDesign()
	//method is called.
	//If there are any validation errors, the details of those errors will be
	//captured in an Errors object that’s passed into processDesign()
	//@ModelAttribute here is to indicate that its value should come from the model
	// and that SPring MCV shouldn't attempt to bind request parameters to it. 
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, 
			@ModelAttribute Order order) {
		
		if(errors.hasErrors()) {
			log.info("error: " + errors);
			return "design";
		}
		
		log.info("processing design: " + design);
		
		//uses the injected TacoRepository to save the taco
		Taco saved = designRepo.save(design);
		//adds the Taco object to the order that's kept in the session
		order.addDesign(saved);
		
		return "redirect:/orders/current"; // once the processDesign() completes, the user's browser should be redirected to the relative path /order/current
		//Save the taco design
		
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

	    return ingredients
	    		.stream()
	            .filter(x -> x.getType().equals(type))
	            .collect(Collectors.toList());

	}
}
