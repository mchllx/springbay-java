package sg.edu.nus.iss.springbay.repositories;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.springbay.models.Product;

@Repository
public class ProductRepo {

    @Autowired @Qualifier("prodCache")
    private RedisTemplate<String, String> template;

        private Logger logger = Logger.getLogger(ProductRepo.class.getName());

    public void saveProducts(List<Product> prodList) {
        if (prodList.isEmpty()) {
            logger.info("Saving failed: Product is empty"); 
        } else {
            prodList.stream()
                .forEach(product -> {
                    String redisRec = "%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s".formatted(
                        product.getId().toString(), product.getTitle(), product.getDescription(),
                        product.getPrice().toString(), product.getDiscountPercentage().toString(),
                        product.getRating().toString(), product.getStock().toString(), product.getBrand(),
                        product.getCategory(), product.getThumbnail(), product.getImages());
                    template.opsForList().leftPush("products", redisRec);
                        //Integer, String, String, Double, Double, Double, Integer, String, String, String, String(11)
                });
        }
    }
        
    public Long getNumberOfProducts(String key) {
        return template.opsForList().size(key);
    }

    public List<String> getAllProduct(String key) {
        return template.opsForList().range(key, 0, -1);
    }

    public String getProduct(String key, int index) {
        return template.opsForList().index(key, index);
       }

    public void deleteProducts(String key) {
        if (hasProducts(key) > 1) {
        template.delete(key);
        }
    }

    public Long hasProducts(String key) {
        return template.opsForList().size(key);
    }

    
}
