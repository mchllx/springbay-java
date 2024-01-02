package sg.edu.nus.iss.springbay.controllers;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.springbay.Utils;
import sg.edu.nus.iss.springbay.models.Form;
import sg.edu.nus.iss.springbay.models.Product;
import sg.edu.nus.iss.springbay.models.User;
import sg.edu.nus.iss.springbay.repositories.ProductRepo;
import sg.edu.nus.iss.springbay.services.ProductService;

@Controller
@RequestMapping(path={"/", "index"})
public class HomeController {

    @Autowired
    ProductService prodSvc;

    private Logger logger = Logger.getLogger(HomeController.class.getName());

    @GetMapping
    public String showHome(@ModelAttribute("product") Product product, Model model, HttpSession sess) {
        User user = (User) sess.getAttribute("user");
        if (user == null) {
            user = new User();
        }

        List<Product> prodListDB = prodSvc.getAllProduct("products");
        prodSvc.saveProducts(prodListDB);

        List<Product> prodList = prodSvc.getProduct("products");
        prodSvc.saveProducts(prodList);
        List<String> catList = prodSvc.getAllCategory("category");
        List<String> menuList = prodSvc.getMenu();
        
        if (prodList.isEmpty()) {
            logger.info("Error: ProductList is empty or returns null");

        } else {
            logger.info("Success: Products found" + prodList);
            logger.info("Success: Categories found" + catList);

            prodSvc.saveCategories(catList);
            model.addAttribute("user", user);
            model.addAttribute("product", prodList);
            model.addAttribute("category", catList);
            model.addAttribute("menu", menuList);
        }

        //if model does not contain object
        if (model.equals(null)) {
            logger.info("Error: Product not found");
            return "error";
        } else {
        return "index";
        }
    }

    @GetMapping(path={"/product"})
    public String showProduct(@ModelAttribute("product") Product product, Form form, Model model, HttpSession sess) {
        return "product";
    }

    @GetMapping(path={"/product/{id}"})
    public String showProductId(@PathVariable("id") Integer id, HttpSession sess,
    @ModelAttribute("product") Product product, Form form, Model model) {
        List<Product> prodListDB = prodSvc.getAllProduct("products");
        prodSvc.saveProducts(prodListDB);

        List<Product> prodList = prodSvc.getProduct("products");
        prodSvc.saveProducts(prodList);
        System.out.println(id);
        Long index = (id.longValue() -1);

        // System.out.println("--------ProductController-------" + prodList);
        List<String> catList = prodSvc.getAllCategory("category");
        List<Product> indexProd = prodSvc.get("products", index);
        // System.out.println("----------Product at Index-------" + indexProd);
        model.addAttribute("form", new Form());
        model.addAttribute("id", id);
        sess.setAttribute("id", id);
        model.addAttribute("product", indexProd);
        model.addAttribute("category", catList);

        if (model.equals(null)) {
            logger.info("Error: Product not found");
            return "error";
        }
    
        return "product";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam("id") Integer id, @Valid @ModelAttribute("form") Form form, BindingResult bindings, Model model, HttpSession sess) {
        model.addAttribute("form", new Form());

        if (bindings.hasErrors()) {
            return "error";
        }

        if (form.getQty() < 0) {
            return "error";
        }
        // System.out.println("id"+ id);
        System.out.println(form.getQty());
        return "redirect:/product{id}";
    
    }

    @GetMapping("/search")
    public String showAllSearch(Model model) {
        String word = "";
        List<Product> searchList = prodSvc.searchProduct(word);
        // System.out.println("-----------Search List----------" + searchList);

        model.addAttribute("searchResult", searchList);
        model.addAttribute("word", word);
        return "search";
    }
    
    @GetMapping("/search/{word}")
    public String showSearch(@RequestParam("search") String word, Model model) {
        System.out.println("-------------Word:------------" + word);
        List<Product> searchList = prodSvc.searchProduct(word);

        model.addAttribute("searchResult", searchList);
        model.addAttribute("word", word);
        return "search";
    }
}
