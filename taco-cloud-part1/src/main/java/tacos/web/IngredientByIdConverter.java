//as I understand it
//the form will post a list of string as the ingredient
//now the db wants Ingredient
//we need to address this problem.
//spring offers an API for converter
//and it's autoconfiguration will register the converter automatically
//in another words when Spring needs to make convert, the converter is called
//magic.......
//I guess if one has multiple converter, it will be called by the type
//for example here we need to convert from string to Ingredient
//so spring calls this converter
//again magic.....

package tacos.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	
	private IngredientRepository ingredientRepo;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
    
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
	    Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
		return optionalIngredient.isPresent() ?
			   optionalIngredient.get() : null;	}
}