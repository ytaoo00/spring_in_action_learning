package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Taco;

public interface TacoRepository 
		extends CrudRepository<Taco, Long>{
	
	//Saving a taco design requires that you also 
	//save the ingredients associated with that taco to the Taco_Ingredients table.
	Taco save(Taco design);
	
}
