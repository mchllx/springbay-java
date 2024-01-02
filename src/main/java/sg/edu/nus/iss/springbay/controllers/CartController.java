package sg.edu.nus.iss.springbay.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.springbay.Utils;
import sg.edu.nus.iss.springbay.models.Form;
import sg.edu.nus.iss.springbay.models.Product;

@Controller
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public String showCart(Model model, HttpSession sess) {
        sess.getAttribute("id");
        model.addAttribute("product", sess.getAttribute("product"));
        model.addAttribute("id", sess.getAttribute("id"));

        return "cart";
    }

    @PostMapping
    public String addCart(@Valid @ModelAttribute("form") Form form, BindingResult bindings, Model model, HttpSession sess) {

        if (bindings.hasErrors()) {
            return "error";
        }

        if (form.getQty() < 0) {
            return "error";
        }

        //Retrieve cart
        List<Product> cartList = (List<Product>)sess.getAttribute("product");
        sess.setAttribute("cart", cartList);

        System.out.println("--------Product----------" + cartList.get(Utils.F_ID));
        System.out.println("--------Session id----------" + sess.getAttribute("id"));
        model.addAttribute("id", sess.getAttribute("id"));
        model.addAttribute("qty", form.getQty());
        model.addAttribute("product", sess.getAttribute("product"));
        sess.setAttribute("qty", form.getQty());

        // System.out.println("id"+ id);
        // System.out.println(form.getQty());
        return "redirect:/cart";
    
    }
    
}
