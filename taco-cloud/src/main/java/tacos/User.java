package tacos;

import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
//Implementations of UserDetails wil provide some essedntial user info to the framework
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private final String username;
	private final String password;
	private final String fullname;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private final String phoneNumber;
	
	//return a collection of authorities granted to the user
	//here in this case, this method simply returns a collection indicating that all users will have been
	//granted ROLE_USER authority
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	//for now this application has no need to disable users
	//so we simply return true for the following methods.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
