package sg.edu.nus.iss.springbay.models;

import java.util.List;

public class Category {
    
    private List<String> electronics;
    private List<String> healthBeauty;
    private List<String> groceries;
    private List<String> homeLifestyle;
    private List<String> sports;
    private List<String> womens;
    private List<String> mens;

    public Category() {
    }

    public Category(List<String> electronics, List<String> healthBeauty, List<String> groceries, List<String> homeLifestyle, List<String> sports,
            List<String> womens, List<String> mens) {
        this.electronics = electronics;
        this.healthBeauty = healthBeauty;
        this.groceries = groceries;
        this.homeLifestyle = homeLifestyle;
        this.sports = sports;
        this.womens = womens;
        this.mens = mens;
    }

    public List<String> getElectronics() { return electronics; }
    public void setElectronics(List<String> electronics) { this.electronics = electronics; }
    public List<String> getHealthBeauty() { return healthBeauty; }
    public void setHealthBeauty(List<String> healthBeauty) { this.healthBeauty = healthBeauty; }
    public List<String> getGroceries() { return groceries; }
    public void setGroceries(List<String> groceries) { this.groceries = groceries; }
    public List<String> getHomeLifestyle() { return homeLifestyle; }
    public void setHomeLifestyle(List<String> homeLifestyle) { this.homeLifestyle = homeLifestyle; }
    public List<String> getSports() { return sports; }
    public void setSports(List<String> sports) { this.sports = sports; }
    public List<String> getWomens() { return womens; }
    public void setWomens(List<String> womens) { this.womens = womens; }
    public List<String> getMens() { return mens; }
    public void setMens(List<String> mens) { this.mens = mens;}

    @Override
    public String toString() {
        return "Category [electronics=" + electronics + ", healthBeauty=" + healthBeauty + ", groceries=" + groceries
                + ", homeLifestyle=" + homeLifestyle + ", sports=" + sports + ", womens=" + womens + ", mens=" + mens
                + "]";
    }

    
}
