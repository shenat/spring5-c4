package com.sat.web.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.sat.bean.Order;
import com.sat.bean.User;
import com.sat.data.interfaces.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j//Lombok提供
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")//跨请求访问order,order session内共享
public class OrderController {
	
//	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

//	@GetMapping("/current")
//	public String orderForm(Model model) {
//		return "orderForm";
//	}
	@GetMapping("/current")
	public String orderForm(@ModelAttribute Order order) {
		 if (order.getName() == null) {
		      order.setName(order.getUser().getFullname());
	    }
	    if (order.getStreet() == null) {
	      order.setStreet(order.getUser().getStreet());
	    }
	    if (order.getCity() == null) {
	      order.setCity(order.getUser().getCity());
	    }
	    if (order.getState() == null) {
	      order.setState(order.getUser().getState());
	    }
	    if (order.getZip() == null) {
	      order.setZip(order.getUser().getZip());
	    }
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order,Errors errors
							,SessionStatus sessionStatus
							,@AuthenticationPrincipal User user) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		order.setUser(user);
		orderRepo.save(order);
		sessionStatus.setComplete();//重置Session,以便order失效
		return "redirect:/";
	}
}
