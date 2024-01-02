package sg.edu.nus.iss.springbay.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="cart")
public class CartController {

    @GetMapping
    public String showCart() {
        System.out.println("hello");
        return "cart";
    }
    
}
