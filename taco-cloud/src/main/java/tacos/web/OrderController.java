package tacos.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;

import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderRepository orderRepo;
	
	@Autowired
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm(@AuthenticationPrincipal User user, 
		      @ModelAttribute Order order) {
		    if (order.getDeliveryName() == null) {
		      order.setDeliveryName(user.getFullname());
		    }
		    if (order.getDeliveryStreet() == null) {
		      order.setDeliveryStreet(user.getStreet());
		    }
		    if (order.getDeliveryCity() == null) {
		      order.setDeliveryCity(user.getCity());
		    }
		    if (order.getDeliveryState() == null) {
		      order.setDeliveryState(user.getState());
		    }
		    if (order.getDeliveryZip() == null) {
		      order.setDeliveryZip(user.getZip());
		    }
		    
		    return "orderForm";
		  }

	
	//this method accept a User obj as a parameter to ID who is using the applicaiton
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, 
			@AuthenticationPrincipal User user) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		log.info("order submitted: " + order);
		order.setUser(user);
		orderRepo.save(order); //save the order to db
		//clean out the order obj
		sessionStatus.setComplete(); //if you don’t clean it out, the order remains in session,
		return "redirect:/";
	}
}
