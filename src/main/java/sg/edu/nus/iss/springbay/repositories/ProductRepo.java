package sg.edu.nus.iss.springbay.repositories;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.springbay.Utils;
import sg.edu.nus.iss.springbay.models.Product;

@Repository
public class ProductRepo {

    @Autowired @Qualifier(Utils.BEAN_REDIS)
    private RedisTemplate<String, String> template;

        private Logger logger = Logger.getLogger(ProductRepo.class.getName());

    public void saveProducts(List<Product> prodList) {
        if (prodList.isEmpty()) {
            logger.info("Saving failed: No product found"); 
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

    public void saveCategory(List<String> catList) {
        if (catList.isEmpty()) {
            logger.info("Saving failed: No categories found"); 
        } else {
            template.opsForList().leftPushAll("category", catList);
                        //String
        }
    }
        
    public Long getTotal(String key) {
        return template.opsForList().size(key);
    }

    public List<String> getAll(String key) {
        return template.opsForList().range(key, 0, -1);
    }

    public String get(String key, int index) {
        return template.opsForList().index(key, index);
    }

    public void delete(String key) {
        if (exists(key) > 1) {
        template.delete(key);
        }
    }

    public Long exists(String key) {
        return template.opsForList().size(key);
    }

    
}
