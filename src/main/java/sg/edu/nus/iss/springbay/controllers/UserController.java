package sg.edu.nus.iss.springbay.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.springbay.models.User;
import sg.edu.nus.iss.springbay.services.LoginSvc;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    LoginSvc loginSvc;

    private Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping(path="/login")
    public String showLogin(Model model, User user, HttpSession sess) {
        user = (User) sess.getAttribute("user");

        if (user == null) {
            user = new User();
        }

        model.addAttribute("user", user);

        return "login";
    }

    @PostMapping(path="/login")
    public String postLogin(@Valid @ModelAttribute("user") User user, BindingResult bindings, Model model, HttpSession sess) {

        if (bindings.hasErrors()) {
            System.out.println("Binding Errors: " + bindings.getAllErrors());
            return "login";
        }

        if (loginSvc.userExist(user.getUsername(), user.getPassword()) == true) {
            sess.setAttribute("user", user);
            model.addAttribute("user", user.getUsername());
            logger.info("Logged in successfully");
            return "redirect:/";

        } else {
            logger.info("Invalid credentials, login failed");
            return "login";
        }
    }
    
}
