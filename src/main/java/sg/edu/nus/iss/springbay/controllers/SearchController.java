package sg.edu.nus.iss.springbay.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.springbay.models.Product;
import sg.edu.nus.iss.springbay.services.ProductService;

@RestController
@RequestMapping(path="api/search/")
public class SearchController {

    @Autowired
    ProductService prodSvc;

    @GetMapping(path="/{word}", produces="application/json")
    public List<Product> searchProduct(@PathVariable("word") String word) {
        return prodSvc.searchProduct(word);
    }
    
}
