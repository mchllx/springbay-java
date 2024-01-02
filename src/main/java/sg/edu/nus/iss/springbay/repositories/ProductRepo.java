package sg.edu.nus.iss.springbay.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonArray;
import sg.edu.nus.iss.springbay.Utils;
import sg.edu.nus.iss.springbay.models.Product;

@Repository
public class ProductRepo {

    @Autowired @Qualifier("redis")
    private RedisTemplate<String, String> template;

        private Logger logger = Logger.getLogger(ProductRepo.class.getName());

    public void saveProducts(List<Product> prodList) {
        if (prodList.isEmpty()) {
            logger.info("Saving failed: No product found"); 
        } else {
            prodList.stream()
                // .peek(product -> System.out.println("--------------------Processing--------------------" + product))
                .forEach(product -> {
                    String redisRec = "%s,%s,%s,%.2f,%.2f,%.2f,%s,%s,%s,%s,%s".formatted(
                        product.getId().toString(), product.getTitle(), product.getDescription().replace(",","  "),
                        product.getPrice(), product.getDiscountPercentage(),
                        product.getRating(), product.getStock().toString(), product.getBrand(),
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
        return template.opsForList().range(key, 0, 100);
    }

    public List<Product> get(String key, long index) {
        ListOperations<String, String> list = template.opsForList();
        List<Product> prodList = new LinkedList<>();
        for (String i: list.range(key, index, index)) {
            String[] terms = i.split(",");
            List<String> termsList = new ArrayList<>();
            List<String> imgList = new LinkedList<>();
            termsList.addAll(Arrays.asList(terms));
                imgList = termsList.subList(Utils.F_IMAGES, termsList.size()); 
                // System.out.println("--------Img List------" + imgList.get(0));
                // System.out.println("--------Img List------" + imgList);
                // System.out.println("--------Img List Last------" + imgList.getLast());
                Product product = new Product();
            try {
                product.setId(Integer.parseInt(termsList.get(Utils.F_ID)));
                product.setTitle(termsList.get(Utils.F_TITLE));
                product.setDescription(termsList.get(Utils.F_DESC));
                product.setPrice(Double.parseDouble(termsList.get(Utils.F_PRICE)));
                product.setDiscountPercentage(Double.parseDouble(termsList.get(Utils.F_DISC)));
                product.setRating(Double.parseDouble(termsList.get(Utils.F_RATING)));
                product.setStock(Integer.parseInt(termsList.get(Utils.F_STOCK)));
                product.setBrand((termsList.get(Utils.F_BRAND)));
                product.setCategory((termsList.get(Utils.F_CAT)));
                product.setThumbnail((termsList.get(Utils.F_THUMB)));

                StringBuilder str = new StringBuilder();
                for (int j = 0; j < imgList.size(); j++) {
                    if (j == imgList.size()-1) {
                        str.append(imgList.get(j));
                    } else {
                        str.append(imgList.get(j));
                        // System.out.println(str);
                        str.append(",");
                    }
                }
                str.insert(0, "[");
                str.insert(str.length(), "]");

                // System.out.println("-----After Process------" + str);
                product.setImages(str.toString());
            prodList.add(product);
            // System.out.println("----------Product from Service:-----------" + product); 
            // System.out.println("----------ProductList from Service:-----------" + prodList);
            } catch(NumberFormatException e3) {
                logger.info("Error setting object attributes");
                e3.printStackTrace();
            } 
        }
        return prodList;
    }

    public List<Product> getProduct(String key) {
        ListOperations<String, String> list = template.opsForList();
        Long size = list.size(key);
        List<Product> prodList = new LinkedList<>();
        for (String i: list.range(key, 0, size)) {
            String[] terms = i.split(",");
            List<String> termsList = new ArrayList<>();
            List<String> imgList = new LinkedList<>();
            termsList.addAll(Arrays.asList(terms));
            // System.out.println("--------termsList--------" + termsList.toString());
            // System.out.println("--------termsList size--------" + termsList.size());
                imgList = termsList.subList(Utils.F_IMAGES, termsList.size()); 
                // System.out.println("--------Img List------" + imgList.get(0));
                // System.out.println("--------Img List------" + imgList);
                // System.out.println("--------Img List Last------" + imgList.getLast());
                Product product = new Product();
            try {
                product.setId(Integer.parseInt(termsList.get(Utils.F_ID)));
                product.setTitle(termsList.get(Utils.F_TITLE));
                product.setDescription(termsList.get(Utils.F_DESC));
                product.setPrice(Double.parseDouble(termsList.get(Utils.F_PRICE)));
                product.setDiscountPercentage(Double.parseDouble(termsList.get(Utils.F_DISC)));
                product.setRating(Double.parseDouble(termsList.get(Utils.F_RATING)));
                product.setStock(Integer.parseInt(termsList.get(Utils.F_STOCK)));
                product.setBrand((termsList.get(Utils.F_BRAND)));
                product.setCategory((termsList.get(Utils.F_CAT)));
                product.setThumbnail((termsList.get(Utils.F_THUMB)));

                StringBuilder str = new StringBuilder();
                for (int j = 0; j < imgList.size(); j++) {
                    if (j == imgList.size()-1) {
                        str.append(imgList.get(j));
                    } else {
                        str.append(imgList.get(j));
                        // System.out.println(str);
                        str.append(",");
                    }
                }
                str.insert(0, "[");
                str.insert(str.length(), "]");

                // System.out.println("-----After Process------" + str);
                product.setImages(str.toString());
            prodList.add(product);
            // System.out.println("----------Product from Service:-----------" + product); 
            // System.out.println("----------ProductList from Service:-----------" + prodList);
            } catch(NumberFormatException e3) {
                logger.info("Error setting object attributes");
                e3.printStackTrace();
            } 
        }
    // } 
        return prodList;
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
