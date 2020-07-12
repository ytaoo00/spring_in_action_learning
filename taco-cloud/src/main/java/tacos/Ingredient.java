//domain --- subject area that the web application address
package tacos;

//lombok liberary: automatically generate methods like getter, setter, equals, etc
import lombok.Data;
import lombok.RequiredArgsConstructor;

//the @Data annotation at the class level is provided by Lombok and
//tells lombok to generate methods
//as well as a constructer that accepts all final propertis as arguments.
@Data
@RequiredArgsConstructor
public class Ingredient {
	private final String id;
	private final String name;
	private final Type type;
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}