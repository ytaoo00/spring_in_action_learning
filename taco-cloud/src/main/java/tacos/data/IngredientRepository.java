//interface that captures the essence of what you need the repository to do
package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {
	
	//Query for all ingredients into a collection of Ingredient objects
	Iterable<Ingredient> findAll();
	
	//Query for a single Ingredient by id
	Ingredient findOne(String id);
	
	//Save an Ingredient object.
	Ingredient save(Ingredient ingredient);
	
}
