package sg.edu.nus.iss.springbay.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.springbay.models.Product;
import sg.edu.nus.iss.springbay.repositories.ProductRepo;
import sg.edu.nus.iss.springbay.services.ProductService;

@Controller
@RequestMapping(path={"/", "index"})
public class HomeController {

    @Autowired
    ProductService prodSvc;

    private Logger logger = Logger.getLogger(HomeController.class.getName());

    @GetMapping
    public ModelAndView showHome(@ModelAttribute("products") Product product) {

        ModelAndView mav = new ModelAndView();

        List<Product> prodList = prodSvc.getAllProduct("products");
        logger.info("Success: Products found" + prodList);
        mav.addObject("product", prodList);

        //if model does not contain object
        if (mav.equals(null)) {
            logger.info("Error: Product not found");
            mav.setStatus(HttpStatusCode.valueOf(500));
            mav.setViewName("error");
            return mav;
        }

        mav.setStatus(HttpStatusCode.valueOf(200));
        mav.setViewName("index");
        return mav;
    }
    
}
