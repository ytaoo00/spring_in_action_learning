//a utility class that converts Taco objects to a new TacoResource Object
//here the TacoResource would be able to carrying link
package tacos.web.api;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import tacos.Taco;

//By annotating TacoResource with @Relation , you can specify how Spring HATEOAS should name the field in the resulting JSON:
//here a single TacoResource obj should be referred in JSON as taco
@Relation(value="taco", collectionRelation="tacos")
//extends ResourceSupport to inherit a list of Link object and methods to manage the list of links.
public class TacoResource extends ResourceSupport {

  private static final IngredientResourceAssembler 
            ingredientAssembler = new IngredientResourceAssembler();
  
  
  //notice that TacoResource doesn't include the id property from Taco.
  //because there is no need to expose any database specific ID in the API
  
  @Getter
  private final String name;

  @Getter
  private final Date createdAt;

  @Getter
  private final List<IngredientResource> ingredients;
  
  //accepts a Taco and copies the pertinent properties from the Taco to its own propertis.
  //notice that u would still need looping to convert a list of Taco objects
  //to address the problem, we create a resource assembler.
  public TacoResource(Taco taco) {
    this.name = taco.getName();
    this.createdAt = taco.getCreatedAt();
    this.ingredients = 
        ingredientAssembler.toResources(taco.getIngredients()); //here we add self link for each ingredient
  }
  
}
