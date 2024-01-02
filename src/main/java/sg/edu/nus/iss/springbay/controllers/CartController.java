package sg.edu.nus.iss.springbay.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path="cart")
public class CartController {

    @GetMapping
    public String showCart(Model model, HttpSession sess) {
        System.out.println("hello");
        sess.getAttribute("user");
        
        return "cart";
    }
    
}
