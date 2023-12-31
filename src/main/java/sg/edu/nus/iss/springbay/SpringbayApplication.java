package sg.edu.nus.iss.springbay;


import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import sg.edu.nus.iss.springbay.models.Product;
import sg.edu.nus.iss.springbay.repositories.ProductRepo;
import sg.edu.nus.iss.springbay.services.ProductService;

@SpringBootApplication
public class SpringbayApplication implements CommandLineRunner {

	@Autowired @Qualifier(Utils.BEAN_REDIS)
	RedisTemplate<String, String> template;
	@Value("${spring.redis.host}")
	String redisHost;
	@Value("${spring.redis.port}")
	String redisPort;
	@Value("${spring.redis.database}")
    private Integer redisDatabase;

	@Autowired
	private ProductService prodSvc;
	
	private Logger logger = Logger.getLogger(SpringbayApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(SpringbayApplication.class, args);
	}

	@Override
	public void run(String... args) {
		logger.info("Starting application: SpringBay");

		// System.out.printf("\tRedis running on %s@%s\n", redisHost, redisPort);
		// System.out.printf("\tRedis database %d\n", redisDatabase);
		// System.out.println("\tRedis template instance: " + template);

		// this list is returning null
		List<Product> prodList = prodSvc.getAllProduct("products");
		logger.info("Saving each product into redis");

		// for (Product product: products) {
		// 	System.out.println("\t" + product);
		prodSvc.saveProducts(prodList);

		List<String> catList = prodSvc.getAllCategory("category");
		logger.info("Saving categories into redis");
		prodSvc.saveCategories(catList);

		System.out.println("--------------------Loaded from Cache: Products--------------------" + prodList);
		System.out.println("--------------------Loaded from Cache: Category:--------------------" + catList);
	}
		
}