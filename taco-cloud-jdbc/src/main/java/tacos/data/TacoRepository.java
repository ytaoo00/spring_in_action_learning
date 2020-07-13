package tacos.data;

import tacos.Taco;

public interface TacoRepository {
	
	//Saving a taco design requires that you also 
	//save the ingredients associated with that taco to the Taco_Ingredients table.
	Taco save(Taco design);
	
}
