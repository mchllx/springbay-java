package sg.edu.nus.iss.springbay.models;

import java.util.List;

public class Product {

    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Double discountPercentage;
    private Double rating;
    private Integer stock;
    private String brand;
    private String category;
    private String thumbnail;
    private String images;

    // //total no. of products
    // private Integer total;
    
    // // set 0 or 1 to skip product
    // private Integer skip;

    // // set limit of how many products to retrieve from prodApi
    // private Integer limit; 

    public Product(Integer id, String title, String description, Double price, Double discountPercentage, Double rating,
            Integer stock, String brand, String category, String thumbnail, String images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.brand = brand;
        this.category = category;
        this.thumbnail = thumbnail;
        this.images = images;
    }

    public Product() {
    }
    
    public Product(String category) {
        this.category = category;
    }
     
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id;}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    @Override
    public String toString() {
        return "Product [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
                + ", discountPercentage=" + discountPercentage + ", rating=" + rating + ", stock=" + stock + ", brand="
                + brand + ", category=" + category + ", thumbnail=" + thumbnail + ", images=" + images + "]";
    }

}
