package tacos;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import lombok.Data;

@Data
@Entity
public class Taco {
	
	//we need to declare validation rules on the class that is to be validated.
	
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@ManyToMany(targetEntity=Ingredient.class) //this is used to declare the relationship between a Taco and its associated Ingredient list
	//A Taco can have many Ingredient objects and an Ingredient can be a part of many Tacos
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
	
	@Id
	//Because you’re relying on the database to automatically generate the ID value, 
	//you also annotate the id property with @GeneratedValue , specifying a strategy of AUTO .
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date createdAt;
	
	// this is used to set the createdAt property to the current date and time before Taco is persisted.
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}
	
}
