package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
//	@Autowired
//	DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;
	
	// first we need to configuring a user store
	// by overriding a configure() mehtod defined in the WebSecurityConfigurerAdapter. 
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//to get started
	//this configure method is used for authentication process.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		// code that uses the given AuthenticationManagerBuilder to specify how users will be looked up during authentication
		
		//first example : in memory user store
			//only a few user
			//no change will happen
			//define those users as part of the security configuration.
		//here we configure two users in an in-memory user store. 
		
		/*
		auth
			.inMemoryAuthentication()
				.withUser("buzz")
					.password("infinity")
					.authorities("ROLE_USER")
				.and()
				.withUser("woody")
					.password("bullseye")
					.authorities("ROLE_USER"); 
		*/
		
		//second example : jdbc based user store
			//relational db
		
		/*
		auth
			.jdbcAuthentication() // must set the datasource so that it knows how to access the db
				.dataSource(dataSource)//The Data Source used here is provided by autowiring
				.usersByUsernameQuery(
						"select username, password, enabled from Users" +
						"where username=?")
				.authoritiesByUsernameQuery(
						"select username, authority from UserAuthorities" +
						"where username=?")
				.passwordEncoder(new BCryptPasswordEncoder());
		*/
		
		//third example LDAP-based authentication
		
		/*
		auth
			.ldapAuthentication()
				//.userSearchFilter("(uid= {0})")
				//.groupSearchFilter("member={0}")//here the above two methods are used to provide filters for the base LDAP queries
												 // which are used to serch for users and groups
												// by default the base queries for both users and groups are empty,
												//indicating that the search should be done from the root of the LDAP hirarchy.
												//one can change that by specifying a query base. 
				
			
			//here rather than search from the root, this example specifies that users be searched for where the organizational unit is people
			//group should be searched for where the organizational unit is groups. 
				.userSearchBase("ou=people") //provides a base query for finding users
				.userSearchFilter("(uid= {0})")
				.groupSearchBase("ou=groups") //specifies the base query for finding groups
				.groupSearchFilter("member={0}");
				//.contextSource() //cannot get the .context right forget about it for now.
					//.root("dc=tacocloud,dc=com"); //if not
//					.ldif("classpath:users.ldif");
//				//.url("ldap://tacocloud.com:389/dc=tacocloud,dc=com");				//if remote server

				//.passwordCompare()// instead of a bind operation, perform a comparison operation
				//that involves sending the entered password to the LDAP directory and asking the server to compare the password
				//against a user's password attribute.
				//it is nice because the actually password remains secret on the server side
				//.passwordEncoder(new BCryptPasswordEncoder()) // we add a further security to encrypted the attempted password.
				//.passwordAttribute("passcode")// by default the password given in the login form will be compared with
												//the value of the userPassword attribute in user's LDAP entry
												//if the password is kept in a different attribute, use .passwordAttribute() to specify the password attribute's name

		*/
		
		//fourth example : customize authentication
			//here we configure the custom user detail service with Spring security
		
		auth
			.userDetailsService(userDetailsService)
			//It would appear that you call the encoder() method and pass its return value to passwordEncoder() .
			//In reality, however, because the encoder() method is annotated with @Bean , it will
			//be used to declare a PasswordEncoder bean in the Spring application context. Any
			//calls to encoder() will then be intercepted to return the bean instance from the
			//application context.
			.passwordEncoder(encoder());		
	}
	
	//this is another configure() method that offered by WebSecurityConfigurerAdapter
	//this method accepts an HttpSecurity obj, which can be used to configure
	//how security is handled at the web level. 
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/design", "/order") //requests for /design and /orders are only available to authenticated users
				.access("hasRole('ROLE_USER')") // here the access() method provides a SpEL expression to declare richer security rules 
				.antMatchers("/", "/**").access("permitAll")//all other requests should be permitted for all users
			
			.and() //signifies that you’re finished with the authorization configuration 
			//and are ready to apply some additional HTTP configuration
				.formLogin() // configure the custom login form
				.loginPage("/login") //When Spring Security determines that the user is unauthenticated 
				//and needs to log in, it will redirect them to this path.
				.loginProcessingUrl("/authenticate") ///authenticate POST - process the credentials and if valid authenticate the user
				//ok so this is another magic that spring does.
				//you can also specify this url to anything and specify the form action in view to this address.
				.usernameParameter("user")
				.passwordParameter("pwd")
			
			.and()
				.logout()
					.logoutSuccessUrl("/")
					
			//Make H2-Console non-secured; for debug purposes		
			.and()
				.csrf()
					.ignoringAntMatchers("/h2-console/**")
					
			//allow pages to be loaded in frames from the same origin; needed for H2-Console
			.and()
				.headers()
					.frameOptions()
						.sameOrigin()
			; 
			//very important!!!!!
			//the order of the above rule matters
			//Security rules declared first take precedence over those declared lower down.
		//If you were to swap the order of those two security rules, all requests would have permitAll() applied to them; 
		//the rule for /design and /orders requests would have no effect.
	
	}
}
