package sg.edu.nus.iss.springbay.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.springbay.SpringbayApplication;
import sg.edu.nus.iss.springbay.models.Product;
import sg.edu.nus.iss.springbay.repositories.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo prodRepo;

    private List<Product> prodList = null;

    private Logger logger = Logger.getLogger(ProductService.class.getName());
   
    // GET
    // fetch('https://dummyjson.com/RESOURCE/?limit=10&skip=5&select=key1&select=key2&select=key3');
    // Stores in a list of Product objects (values are assigned to Product obj)
    public List<Product> getAllProduct(String key) {
        
        // optional when it may or may not return data (cache is empty vs not empty)
        List<String> list = prodRepo.getAllProduct(key);
        System.out.println("--------------------List from Redis:--------------------" + list);
        String payload;
        JsonArray array;

        // construct into a uri template 
        // if (list.isEmpty()) {
            String url = UriComponentsBuilder
                .fromUriString("https://dummyjson.com/products")
                .queryParam("products")
                .toUriString();

            //retrieve a Resource object of json type from external API
            //httpmethod, URI (values from "url"), type (data type)
            RequestEntity<Void> req = RequestEntity
                .get(url) .accept(MediaType.APPLICATION_JSON) .build();

            RestTemplate template = new RestTemplate();
            //json values are stored within the response body/payload
            ResponseEntity<String> resp = null;

            /**execute GET http method from "url" uri template
             * write to "req" RequestEntity
             * return body of type String "resp" ResponseEntity**/

            try {

            resp = template.exchange(req, String.class);

            } catch (RestClientException e1) {
                logger.info("Request Failed: Server Error Response");
                e1.printStackTrace();
                return new LinkedList<>();
            }

            //if no 500 server error, resp is not empty
            //values from body
            payload = resp.getBody();
            // System.out.println(payload);

            //create json structure same as API
            //read the string
            //write into jsonobject
            prodList = new ArrayList<>();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject obj = reader.readObject();
            // System.out.println("Results:" + obj);

            array = obj.getJsonArray("products"); 

            // System.out.println("--------------------Results from API:--------------------" + array);
        
            //convert from Json to Java POJO
            // products [{..., [...]}]
            prodList = array.stream()
                .map(j -> j.asJsonObject())
                // .peek(json -> System.out.println("--------------------Processing--------------------" + json))
                .map(o -> {
            try {
                Integer id = o.getInt("id", 0);
                String title = o.getString("title", "Test");
                String description = o.getString("description", "This is a test product");
                Double price = Double.parseDouble(o.getString("price", "100.0"));
                Double discountPercentage = Double.parseDouble(o.getString("discountPercentage", "10.00"));
                Double rating = Double.parseDouble(o.getString("rating", "5.00"));
                Integer stock = o.getInt("stock", 100);
                String brand = o.getString("brand", "Springbay");
                String category = o.getString("category", "No category");
                String thumbnail = o.getString("thumbnail", "https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg");

                //["...", "...", "...", "..."]

                JsonArray imgArray = o.getJsonArray("images");
                int size = imgArray.size();
                String[] imgUrl = new String[size];
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < size; i++){
                    imgUrl[i] = imgArray.getJsonString(i).getString();
                        if (i == size-1) {
                            str.append(imgUrl[i]);
                        } else {
                            str.append(imgUrl[i]);
                            str.append("," + " ");
                        }
                }
                str.insert(0, "[");
                str.insert(str.length(), "]");
                String images = str.toString(); 
                // System.out.println("--------------------Images--------------------" + images); 

                return new Product(id, title, description, price, discountPercentage,
                rating, stock, brand, category, thumbnail, images);

                } catch (Exception e2) {
                    logger.info("Processing Failed: Error processing JSON");
                    e2.printStackTrace();
                    return new Product();
                }
            })

            .toList();
            System.out.println("--------------------Results from Stream:--------------------" + prodList);

            return prodList;
    }

    public void getProduct(String key, int index) {
        prodRepo.getProduct(key, index);
    }

    public void saveProducts(List<Product> prodList) {
        logger.info("Clearing database: Redis");
		prodRepo.deleteProducts("products");
        prodRepo.saveProducts(prodList);
    }
}

 // } else {
        //     //convert from String to Json
        //     payload = list.toString();
        //     JsonReader reader = Json.createReader(new StringReader(payload));

        //     JsonBuilderFactory fac = Json.createBuilderFactory(null);
        //     JsonObject results = Json.createObjectBuilder()
        //         .add("products", fac.createArrayBuilder()
        //             .add(fac.createObjectBuilder()
        //             .add("id", 0)
        //             .add("title", "Test")
        //             .add("description", "This is a test product")
        //             .add("price", 100.0)
        //             .add("discountPercentage", 10.00)
        //             .add("rating", 5.00)
        //             .add("stock", 100)
        //             .add("brand", "Springbay")
        //             .add("category", "test")
        //             .add("thumbnail", "https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
        //             .add("images", fac.createArrayBuilder()
        //                 .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
        //                 .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
        //                 .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
        //                 .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
        //             )))
        //         .add("total", 100)
        //         .add("skip", 0)
        //         .add("limit", 30)
        //         .build();
        //     System.out.println("--------------------Payload from Cache:--------------------" + payload);

        // }
        // return prodList; 

// if (opt.isEmpty()) {
//             JsonBuilderFactory fac = Json.createBuilderFactory(null);
//             JsonObject results = Json.createObjectBuilder()
//                 .add("products", fac.createArrayBuilder()
//                     .add(fac.createObjectBuilder()
//                     .add("id", 0)
//                     .add("title", "Test")
//                     .add("description", "This is a test product")
//                     .add("price", 100.0)
//                     .add("discountPercentage", 10.00)
//                     .add("rating", 5.00)
//                     .add("stock", 100)
//                     .add("brand", "Springbay")
//                     .add("category", "test")
//                     .add("thumbnail", "https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
//                     .add("images", fac.createArrayBuilder()
//                         .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
//                         .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
//                         .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
//                         .add("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg")
//                     )))
//                 .add("total", 100)
//                 .add("skip", 0)
//                 .add("limit", 30)
//                 .build();

//pass null as config
            //unless WriterFactory, takes in a Map<String, ?/Object>
            // Map<String, Object> config = new HashMap<>();
            // config.put(JsonGenerator.PRETTY_PRINTING, Boolean.valueOf(true));
            // JsonBuilderFactory fac = Json.createBuilderFactory(null);
            // System.out.println("--------------------Writing to JSON:--------------------");
            // JsonObject results = Json.createObjectBuilder()
            //     .add("products", fac.createArrayBuilder(array))
            //     .build();

            // System.out.println("--------------------Results as JSON:--------------------" + results);