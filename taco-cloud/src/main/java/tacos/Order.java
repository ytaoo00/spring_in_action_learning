package tacos;

import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;
import javax.validation.constraints.NotBlank;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;



@Data
@Entity
@Table(name="Taco_Order") //This specifies that Order entities should be persisted to a table named Taco_Order in the database.
//notice that here the @Table annotation is necessary with order
//because order is reserved word in SQL and would cause problems.
public class Order implements Serializable{
	
	  private static final long serialVersionUID = 1L;
	
	  @NotBlank(message="Delivery name is required")
	  private String deliveryName;
	  
	  @NotBlank(message="Street is required")
	  private String deliveryStreet;
	  
	  @NotBlank(message="City is required")
	  private String deliveryCity;
	  
	  @NotBlank(message="State is required")
	  private String deliveryState;
	  
	  @NotBlank(message="Zip code is required")
	  private String deliveryZip;

	  @CreditCardNumber(message="Not a valid credit card number")
	  private String ccNumber;
	  
	  @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
	           message="Must be formatted MM/YY")
	  private String ccExpiration;

	  @Digits(integer=3, fraction=0, message="Invalid CVV")
	  private String ccCVV;
	  
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long id;
	  
	  private Date placedAt;

	  @ManyToMany(targetEntity=Taco.class)
	  private List<Taco> tacos = new ArrayList<>();
	  
	  @ManyToOne //an order belongs to a single user, and, conversely, that a user may have many orders
	  private User user;
	  
	  public void addDesign(Taco design) {
		this.tacos.add(design);	
	  }
	  
	  @PrePersist
	  void placedAt() {
		  this.placedAt = new Date();
	  }
}
