package sg.edu.nus.iss.springbay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.springbay.models.User;
import sg.edu.nus.iss.springbay.services.LoginService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    LoginService loginSvc;

    @GetMapping(path="/user")
    public String showProfile(Model model, HttpSession sess, User user) {
            sess.getAttribute("user");
            
        return "user";
    }

    @GetMapping(path="/user/{username}")
    public String showUser(Model model, HttpSession sess, User user) {
            sess.getAttribute("user");
            
        return "user";
    }
    
}
