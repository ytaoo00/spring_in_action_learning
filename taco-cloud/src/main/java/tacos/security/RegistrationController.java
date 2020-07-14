//presents and processes registration forms

package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	
	//somehow the book says as follow
	//You’ve no doubt noticed that RegistrationController is injected with a PasswordEncoder . 
	//This is the exact same PasswordEncoder bean you declared before.
	//does it means here we don't need the @Autowired for injection....
	//magic.... look into it later.
	//probably look into the usage of @Autowired and the scope of beans
	
	private UserRepository userRepo;
	private PasswordEncoder  passwordEncoder;
	
	//why not use @autowired here 
	public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(RegistrationForm form) {
		userRepo.save(form.toUser(passwordEncoder)); //bound the RegistrationForm object to the request data
		return "redirect:/login";
	}
}
