//domain --- subject area that the web application address
//in this class, we Annotating Ingredient for JPA persistence
package tacos;

import javax.persistence.Entity;
import javax.persistence.Id;


//lombok liberary: automatically generate methods like getter, setter, equals, etc
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//the @Data annotation at the class level is provided by Lombok and
//tells lombok to generate methods
//as well as a constructer that accepts all final propertis as arguments.
@Data
//The @Data implicitly adds a required arguments constructor, 
//but when a @NoArgsConstructor is used, that constructor get removed
//an explicit @RequiredArgsConstructor ensures that you will still have
//a required arguments constructor in addition to the private no-arguments constructor
@RequiredArgsConstructor 
//JPA requires that entities have a no-arguments constructor
//we set the constructor to be private so that we would not be able to use this particular constructor
//because there are final properties that must be set, 
//you also set the force attribute to true , 
//which results in the Lombok-generated constructor setting them to null .
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true) 
@Entity // by using this annotation, we declare this as a JPA entity
public class Ingredient {
	
	@Id //indicates this property as a unique identifier for the enetity
	private final String id;
	private final String name;
	private final Type type;
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}